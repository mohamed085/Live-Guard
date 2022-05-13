package com.liveguard.controller;

import com.liveguard.dto.AnswerDTO;
import com.liveguard.dto.QuestionDTO;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody AnswerDTO answerDTO) {
        log.debug("AnswerController | add | answerDTO: " + answerDTO.toString());
        answerService.save(answerDTO);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Answer added successfully"));
    }

}
