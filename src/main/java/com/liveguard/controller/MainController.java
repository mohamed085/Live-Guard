package com.liveguard.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liveguard.domain.Notification;
import com.liveguard.domain.User;
import com.liveguard.service.AccountService;
import com.liveguard.service.SendNotificationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController {

    private final SendNotificationService sendNotificationService;
    private final AccountService accountService;

    public MainController(SendNotificationService sendNotificationService, AccountService accountService) {
        this.sendNotificationService = sendNotificationService;
        this.accountService = accountService;
    }

    @RequestMapping("/test")
    public void test() {
        User user = accountService.getAuthenticatedAccount();
        Notification notification = new Notification();
        notification.setContent("Hi: " + user.getName());

        sendNotificationService.sendGlobalNotification(notification);
        sendNotificationService.sendPrivateNotification(notification, user);
    }

}
