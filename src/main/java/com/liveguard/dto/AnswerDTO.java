package com.liveguard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {

    private Long id;

    @JsonProperty("answer")
    private String answerContent;

    private boolean approved;

    @JsonProperty("answer_time")
    @JsonFormat(pattern="dd MMMM yyyy")
    private LocalDateTime answerTime;

    @JsonProperty("question_id")
    private Long questionId;

    private SimpleUserDTO user;
}
