package com.liveguard.service.serviceImp;

import com.liveguard.domain.Notification;
import com.liveguard.domain.User;
import com.liveguard.service.NotificationService;
import com.liveguard.service.SendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SendNotificationServiceImp implements SendNotificationService {


    @Override
    public void sendToSpecificUser(Notification notification, User user) {

    }

    @Override
    public void sendToMultipleUser(Notification notification, List<User> users) {

    }

    @Override
    public void sendToAdmins(Notification notification) {

    }

}
