package com.liveguard.dto;

import com.liveguard.domain.Day;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDayDTO {
    private Long id;
    private Day day;
}
