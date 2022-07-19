package com.liveguard.service.serviceImp;

import com.liveguard.domain.Notification;
import com.liveguard.dto.NotificationDTO;
import com.liveguard.mapper.NotificationMapper;
import com.liveguard.service.NotificationService;
import com.liveguard.service.SendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendNotificationServiceImp implements SendNotificationService {

    private final SimpMessagingTemplate template;
    private final NotificationService notificationService;

    public SendNotificationServiceImp(SimpMessagingTemplate template, NotificationService notificationService) {
        this.template = template;
        this.notificationService = notificationService;
    }

    @Override
    @Async
    public void sendGlobalNotification(Notification notification) {
        NotificationDTO notificationDTO = NotificationMapper.notificationToNotificationDTO(notification);

        log.debug("SendNotificationService | sendGlobalNotification");

        String destination = "/live-guard/notification";

        log.debug("SendNotificationService | sendGlobalNotification | destination: " + destination);
        log.debug("SendNotificationService | sendGlobalNotification | message: " + notificationDTO);

        template.convertAndSend(destination, notificationDTO);

    }

    @Override
    @Async
    public void sendPrivateNotification(Notification notification) {
        NotificationDTO notificationDTO = NotificationMapper.notificationToNotificationDTO(notification);

        log.debug("SendNotificationService | sendPrivateNotification | user id: " + notification.getUser().getId());

        notificationService.save(notification);

        String destination = "/live-guard/notification/" + notification.getUser().getId();

        log.debug("SendNotificationService | sendPrivateNotification | destination: " + destination);
        log.debug("SendNotificationService | sendPrivateNotification | message: " + notificationDTO);

        template.convertAndSend(destination, notificationDTO);

    }

}
