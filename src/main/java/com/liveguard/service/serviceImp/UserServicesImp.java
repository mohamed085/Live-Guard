package com.liveguard.service.serviceImp;

import com.liveguard.domain.Chip;
import com.liveguard.domain.Role;
import com.liveguard.domain.User;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.UserDTO;
import com.liveguard.dto.SimpleUserDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.ChipMapper;
import com.liveguard.mapper.UserMapper;
import com.liveguard.repository.RoleRepository;
import com.liveguard.repository.UserRepository;
import com.liveguard.service.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServicesImp implements UserServices {

    public static final int USERS_PER_PAGE = 6;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserServicesImp(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findById(Long id) {
        log.debug("UserService | findById | user id: " + id);

        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("User not found", HttpStatus.NOT_FOUND));

            return UserMapper.userToUserDTO(user);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SimpleUserDTO> findAllCustomersInSimpleForm() {
        log.debug("UserService | getAllCustomers");

        try {
            Role customerRole = roleRepository.findByRole("Customer");

            List<SimpleUserDTO> customers = new ArrayList<>();
            userRepository.findAllByRoles(customerRole).forEach(user -> {
                customers.add(new SimpleUserDTO(user.getId(), user.getEmail(), user.getName(), user.getAbout(), user.getAvatar()));
            });

            return customers;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<UserDTO> findAllByPage(int pageNum, String sortField, String sortDir, String keyword) {

        log.debug("UserService | findAllByPage");
        log.debug("UserService | findAllByPage | pageNum: " + pageNum);
        log.debug("UserService | findAllByPage | sortField: " + sortField);
        log.debug("UserService | findAllByPage | sortDir: " + sortDir);
        log.debug("UserService | findAllByPage | keyword: " + keyword);

        try {
            if (sortField == null) {
                sortField = "id";
            }

            if (sortDir == null) {
                sortDir = "asc";
            }

            Sort sort = Sort.by(sortField);
            sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

            Pageable pageable = PageRequest.of(pageNum - 1 , USERS_PER_PAGE, sort);

            Page<User> users;

            if (keyword == null) {
                users = userRepository.findAll(pageable);
            } else {
                users = userRepository.findAll(keyword, pageable);
            }

            Page<UserDTO> userDTOPage = users.map(user -> {
                UserDTO userDTO = UserMapper.userToUserDTO(user);
                return userDTO;
            });

            return userDTOPage;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ChipService | findAllByPage | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }    }
}
