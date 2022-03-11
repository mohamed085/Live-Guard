package com.liveguard.dto;

import com.liveguard.domain.EnumDay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayDTO {
    private Long id;
    private EnumDay day;
}
