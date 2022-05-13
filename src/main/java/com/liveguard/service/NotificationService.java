package com.liveguard.service;

import com.liveguard.domain.Notification;
import com.liveguard.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {

    void save(Notification notification);

    Page<Notification> findAllByUserId(int pageNum);
}
