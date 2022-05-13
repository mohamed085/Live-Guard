package com.liveguard.controller;

import com.liveguard.dto.QuestionDTO;
import com.liveguard.dto.ReviewDTO;
import com.liveguard.mapper.QuestionMapper;
import com.liveguard.mapper.ReviewMapper;
import com.liveguard.payload.ApiResponse;
import com.liveguard.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody QuestionDTO questionDTO) {
        log.debug("QuestionController | add | reviewDTO: " + questionDTO.toString());
        questionService.save(questionDTO);

        return ResponseEntity
                .ok()
                .body(new ApiResponse(true, "Question added successfully"));
    }

    @GetMapping("/{chipVersionId}")
    public ResponseEntity<?> getFirstPage(@PathVariable("chipVersionId") Long chipVersionId) {
        log.debug("QuestionController | getFirstPage");

        return getAllByPage(chipVersionId, 1);
    }

    @GetMapping("/{chipVersionId}/page/{pageNum}")
    public ResponseEntity<?> getAllByPage(@PathVariable("chipVersionId") Long chipVersionId,
                                          @PathVariable(name = "pageNum") int pageNum) {

        log.debug("QuestionController | getAllByPage");
        log.debug("QuestionController | getAllByPage | pageNum: " + pageNum);

        Page<QuestionDTO> questionDTOS = questionService.findAllByChipVersionId(chipVersionId, pageNum)
                .map(question -> {
                    QuestionDTO questionDTO = QuestionMapper.questionTOQuestionDTO(question);
                    return questionDTO;
                });

        return ResponseEntity
                .ok()
                .body(questionDTOS);
    }

}
