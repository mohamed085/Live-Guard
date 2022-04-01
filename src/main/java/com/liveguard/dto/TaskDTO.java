package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;

    @NotNull(message = "Name must not null")
    @NotEmpty(message = "Name must not empty")
    private String name;

    private String description;

    @NotNull(message = "Ringtone must not null")
    @NotEmpty(message = "Ringtone must not empty")
    private String ringtone;

    @NotNull(message = "Lat must not null")
    @NotEmpty(message = "Lat must not empty")
    private Double lat;

    @NotNull(message = "Lng must not null")
    @NotEmpty(message = "Lng must not empty")
    private Double lng;

    @NotNull(message = "Area must not null")
    @NotEmpty(message = "Area must not empty")
    private Double area;

    private Boolean mute;

    @NotNull(message = "Start time must not null")
    @NotEmpty(message = "Start time not empty")
    @JsonProperty("start_time")
    @JsonFormat(pattern="h:mm a")
    private LocalTime startTime;

    @NotNull(message = "End time not null")
    @NotEmpty(message = "End time not empty")
    @JsonProperty("end_time")
    @JsonFormat(pattern="h:mm a")
    private LocalTime endTime;

    @JsonProperty("create_date")
    @JsonFormat(pattern="dd MMMM yyyy h:mm a")
    private LocalDateTime createDate;

    @NotNull(message = "Repeats must not null")
    @NotEmpty(message = "Repeats must not empty")
    @JsonProperty("repeat_id")
    private List<Long> repeatId;

    private Set<Day> repeat;

    private SimpleChipDTO chip;

    @JsonProperty("add_by_user")
    private SimpleUserDTO addByUser;
}
