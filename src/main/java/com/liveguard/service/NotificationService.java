package com.liveguard.service;

import com.liveguard.domain.Notification;
import com.liveguard.domain.User;

import java.util.List;

public interface NotificationService {

    void save(Notification notification);

    List<Notification> findAllByUserId(Long userId);
}
