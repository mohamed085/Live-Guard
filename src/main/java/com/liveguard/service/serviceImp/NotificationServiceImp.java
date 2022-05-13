package com.liveguard.service.serviceImp;

import com.liveguard.domain.Notification;
import com.liveguard.domain.User;
import com.liveguard.repository.NotificationRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImp implements NotificationService {

    public static final int NOTIFICATIONS_PER_PAGE = 20;
    private final NotificationRepository notificationRepository;
    private final AccountService accountService;

    public NotificationServiceImp(NotificationRepository notificationRepository, AccountService accountService) {
        this.notificationRepository = notificationRepository;
        this.accountService = accountService;
    }

    @Override
    public void save(Notification notification) {
        log.debug("NotificationService | save | notification: " + notification.toString());

        notificationRepository.save(notification);
    }

    @Override
    public Page<Notification> findAllByUserId(int pageNum) {
        User user = accountService.getAuthenticatedAccount();

        log.debug("NotificationService | findAllByUserId");
        log.debug("NotificationService | findAllByUserId | userId: " + user.getId());
        log.debug("NotificationService | findAllByUserId | pageNum: " + pageNum);

        Pageable pageable = PageRequest.of(pageNum - 1 , NOTIFICATIONS_PER_PAGE, Sort.by("createdTime").descending());

        return notificationRepository.findAllByUserId(user.getId(), pageable);
    }

}
