package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liveguard.domain.ChipVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;

    @JsonProperty("question")
    private String questionContent;

    private boolean approved;

    @JsonProperty("ask_time")
    @JsonFormat(pattern="dd MMMM yyyy")
    private LocalDateTime askTime;

    @JsonProperty("chip_version_id")
    private Long chipVersionId;

    private SimpleUserDTO user;

    private List<AnswerDTO> answers;
}
