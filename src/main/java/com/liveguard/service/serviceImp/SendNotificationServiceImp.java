package com.liveguard.service.serviceImp;

import com.liveguard.domain.Notification;
import com.liveguard.domain.User;
import com.liveguard.dto.LocationDTO;
import com.liveguard.mapper.LocationMapper;
import com.liveguard.service.SendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SendNotificationServiceImp implements SendNotificationService {

    private final SimpMessagingTemplate template;

    public SendNotificationServiceImp(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    @Async
    public void sendGlobalNotification(Notification notification) {
        log.debug("SendLocationService | sendGlobalNotification ");

        String destination = "/live-guard/global-notification";

        log.debug("SendLocationService | sendGlobalNotification | destination: " + destination);
        log.debug("SendLocationService | sendGlobalNotification | message: " + notification);

        template.convertAndSend(destination, notification);

    }

    @Override
    @Async
    public void sendPrivateNotification(Notification notification, User user) {
        log.debug("SendLocationService | sendPrivateNotification | user id: " + user.getId());

        String destination = "/live-guard/private-notification";
        String userDestination = String.valueOf(user.getId());

        log.debug("SendLocationService | sendPrivateNotification | destination: " + destination);
        log.debug("SendLocationService | sendPrivateNotification | userDestination: " + userDestination);
        log.debug("SendLocationService | sendPrivateNotification | message: " + notification);

        template.convertAndSendToUser(userDestination, destination, notification);

    }

    @Override
    @Async
    public void sendToMultipleUser(Notification notification, List<User> users) {

    }

    @Override
    @Async
    public void sendToAdmins(Notification notification) {

    }

}
