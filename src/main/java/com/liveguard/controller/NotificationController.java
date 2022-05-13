package com.liveguard.controller;

import com.liveguard.dto.NotificationDTO;
import com.liveguard.mapper.NotificationMapper;
import com.liveguard.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<?> getFirstPage() {

        return getAllByPage(1);
    }

    @GetMapping("/page/{pageNum}")
    public ResponseEntity<?> getAllByPage(@PathVariable(name = "pageNum") int pageNum) {

        log.debug("NotificationController | getAllByPage");
        log.debug("NotificationController | getAllByPage | pageNum: " + pageNum);

        Page<NotificationDTO>  notificationDTOs = notificationService.findAllByUserId(pageNum)
                .map(notification -> {
                    NotificationDTO notificationDTO = NotificationMapper.notificationToNotificationDTO(notification);
                    return notificationDTO;
                });


        return ResponseEntity
                .ok()
                .body(notificationDTOs);
    }

}
