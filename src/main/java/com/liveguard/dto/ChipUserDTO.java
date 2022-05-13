package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.ChipUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
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
    private MultipartFile photoFile;

    @JsonProperty("add_date")
    @JsonFormat(pattern="dd MMMM yyyy h:mm a")
    private LocalDateTime addDate;

    private List<ChipUserDetailDTO> details;

    @JsonProperty("chip_user_type")
    private ChipUserType chipUserType;

    @JsonProperty("chip_version")
    private SimpleChipVersion chipVersion;

    @JsonProperty("chip_id")
    private Long chipId;
}

