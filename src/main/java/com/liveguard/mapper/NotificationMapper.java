package com.liveguard.mapper;

import com.liveguard.domain.Notification;
import com.liveguard.dto.NotificationDTO;
import com.liveguard.util.DurationBetweenDates;
import org.aspectj.weaver.ast.Not;

public class NotificationMapper {

    public static NotificationDTO notificationToNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setSubject(notification.getSubject());
        notificationDTO.setContent(notification.getContent());
        notificationDTO.setRingtone(notification.getRingtone());
        notificationDTO.setPhoto(notification.getPhoto());
        notificationDTO.setAbout(DurationBetweenDates.findDifference(notification.getCreatedTime()));

        return notificationDTO;
    }
}
