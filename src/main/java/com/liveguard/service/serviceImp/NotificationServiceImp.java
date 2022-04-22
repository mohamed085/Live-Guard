package com.liveguard.service.serviceImp;

import com.liveguard.domain.Notification;
import com.liveguard.repository.NotificationRepository;
import com.liveguard.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void save(Notification notification) {

    }

    @Override
    public List<Notification> findAllByUserId(Long userId) {
        return null;
    }
}
