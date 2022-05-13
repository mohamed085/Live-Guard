package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.ChipUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChipUserDTO {
    private Long id;
    private String name;
    private String photo;

    @JsonProperty("photo_file")
    private String photoFile;

    @JsonProperty("add_date")
    @JsonFormat(pattern="dd MMMM yyyy h:mm a")
    private LocalDateTime addDate;

    private SimpleUserDTO user;

    private List<ChipDetailDTO> details;

    @JsonProperty("chip_user_type")
    private ChipUserType chipUserType;
}

