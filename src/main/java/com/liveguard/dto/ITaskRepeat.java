package com.liveguard.dto;

import com.liveguard.domain.EnumDay;

public interface ITaskRepeat {
    Long getId();
    EnumDay getDay();
    Long getTask_id();
    Long getDay_id();
}
