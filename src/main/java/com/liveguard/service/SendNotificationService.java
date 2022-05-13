package com.liveguard.service;

import com.liveguard.domain.Notification;
import com.liveguard.domain.User;

import java.util.List;

public interface SendNotificationService {

    void sendGlobalNotification(Notification notification);
    void sendPrivateNotification(Notification notification);

}
