package com.liveguard.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface ITaskDTO {
    Long getId();
    Double getArea();
    LocalDateTime getCreate_date();
    String getDescription();
    LocalTime getStart_time();
    LocalTime getEnd_time();
    Double getLng();
    Double getLat();
    String getName();
    String getRingtone();
    Long getChip_id();
    Long getAdd_by_user_id();
    Boolean getStatus();
    String getEmail();
    String getUser_name();
    String getAbout();
    String getAvatar();
    String getChip_name();
    String getPhoto();
}
