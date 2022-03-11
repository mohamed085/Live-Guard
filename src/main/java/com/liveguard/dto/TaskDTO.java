package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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

    @NotNull(message = "Start date must not null")
    @NotEmpty(message = "Start date not empty")
    @JsonProperty("start_date")
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime startDate;

    @NotNull(message = "End date not null")
    @NotEmpty(message = "End date not empty")
    @JsonProperty("end_date")
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime endDate;

    @NotNull(message = "Create date must not null")
    @NotEmpty(message = "Create date must not empty")
    @JsonProperty("create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @NotNull(message = "Repeats must not null")
    @NotEmpty(message = "Repeats must not empty")
    @JsonProperty("repeat_id")
    private List<Long> repeatId;

    private List<DayDTO> repeat;
    private ChipSimpleDataDTO chip;

    @JsonProperty("add_by_user")
    private UserSimpleDataDTO addByUser;
}
