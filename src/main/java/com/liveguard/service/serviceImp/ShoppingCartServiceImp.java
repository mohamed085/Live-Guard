package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipVersion;
import com.liveguard.domain.ShoppingCart;
import com.liveguard.domain.ShoppingCartItem;
import com.liveguard.domain.User;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.ShoppingCartItemRepository;
import com.liveguard.repository.ShoppingCartRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipVersionService;
import com.liveguard.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class ShoppingCartServiceImp implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final AccountService accountService;
    private final ChipVersionService chipVersionService;

    public ShoppingCartServiceImp(ShoppingCartRepository shoppingCartRepository,
                                  ShoppingCartItemRepository shoppingCartItemRepository,
                                  AccountService accountService, ChipVersionService chipVersionService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.accountService = accountService;
        this.chipVersionService = chipVersionService;
    }

    @Override
    public ShoppingCart findMyShoppingCart() {
        log.debug("ShoppingCartService | findMyShoppingCart");
        User user = accountService.getAuthenticatedAccount();

        return shoppingCartRepository.findByCustomerId(user.getId())
                .orElseThrow(() -> new BusinessException("You have not chosen any products yet.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void addChipVersionToMyCart(Long chipVersionId, Integer quantity) {
        log.debug("ShoppingCartService | addChipVersionToMyCart | chipVersionId: " + chipVersionId);
        log.debug("ShoppingCartService | addChipVersionToMyCart | quantity: " + quantity);
        User user = accountService.getAuthenticatedAccount();

        ChipVersion chipVersion = chipVersionService.findById(chipVersionId);

        ShoppingCart shoppingCart;
        ShoppingCartItem shoppingCartItem = null;
        if (shoppingCartRepository.existsByCustomerId(user.getId())) {
            shoppingCart = findMyShoppingCart();
            log.debug("ShoppingCartService | addChipVersionToMyCart | shoppingCart: " + shoppingCart.getId());

            if (shoppingCartItemRepository.existsByChipVersionIdAndAndShoppingCartId(chipVersion.getId(), shoppingCart.getId())) {

                shoppingCartItem = shoppingCartItemRepository.findByChipVersionIdAndShoppingCartId(chipVersion.getId(), shoppingCart.getId());
                log.debug("ShoppingCartService | addChipVersionToMyCart | shopping cart item: " + shoppingCartItem.toString());
                updateCartItemQuantity(shoppingCartItem.getId(), quantity + shoppingCartItem.getQuantity());

            } else {
                log.debug("ShoppingCartService | addChipVersionToMyCart | create new shopping cart item");
                ShoppingCartItem cartItem = createNewShoppingCartItem(shoppingCart, quantity, chipVersion);

                log.debug("ShoppingCartService | addChipVersionToMyCart | update shopping cart item");

                updateMyShoppingCart();
            }

        } else {
            log.debug("ShoppingCartService | addChipVersionToMyCart | create new shopping cart");
            shoppingCart = createNewShoppingCart(user);


            log.debug("ShoppingCartService | addChipVersionToMyCart | create new shopping cart item");
            ShoppingCartItem cartItem = createNewShoppingCartItem(shoppingCart, quantity, chipVersion);

            log.debug("ShoppingCartService | addChipVersionToMyCart | update shopping cart item");
            updateMyShoppingCart();
        }
    }

    @Override
    @Transactional
    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        log.debug("ShoppingCartService | updateCartItem | cartItemId: " + cartItemId);
        log.debug("ShoppingCartService | updateCartItem | quantity: " + quantity);

        ShoppingCartItem cartItem = shoppingCartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BusinessException("Item not found", HttpStatus.NOT_FOUND));

        float total = quantity * cartItem.getChipVersion().getDiscountPrice();
        shoppingCartItemRepository.updateInfo(cartItem.getId(), quantity, total);

        updateMyShoppingCart();
    }

    private ShoppingCart createNewShoppingCart(User user) {
        log.debug("ShoppingCartService | createNewShoppingCart | user: " + user.toString());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(user);

        return shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCartItem createNewShoppingCartItem(ShoppingCart shoppingCart, Integer quantity, ChipVersion chipVersion) {
        log.debug("ShoppingCartService | createNewShoppingCartItem | shoppingCart: " + shoppingCart.toString());
        log.debug("ShoppingCartService | createNewShoppingCartItem | quantity: " + quantity);

        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setChipVersion(chipVersion);
        cartItem.setQuantity(quantity);
        cartItem.setTotal(chipVersion.getDiscountPrice() * quantity);
        cartItem.setShoppingCart(shoppingCart);

        return shoppingCartItemRepository.save(cartItem);
    }

    @Transactional
    public void updateMyShoppingCart() {
        log.debug("ShoppingCartService | updateMyShoppingCart");

        ShoppingCart shoppingCart = findMyShoppingCart();

        int quantity = 0;
        float productsTotal = 0;

        for (ShoppingCartItem item: shoppingCartItemRepository.findAllByShoppingCartId(shoppingCart.getId())) {
            log.debug("ShoppingCartService | updateShoppingCart | item: " + item.toString());
            quantity += item.getQuantity();
            productsTotal += item.getTotal();
        }

        log.debug("ShoppingCartService | updateShoppingCart | quantity: " + quantity);
        log.debug("ShoppingCartService | updateShoppingCart | productsTotal: " + productsTotal);

        shoppingCartRepository.updateInfo(shoppingCart.getId(), quantity, productsTotal);
    }


}
