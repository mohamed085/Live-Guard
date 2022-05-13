package com.liveguard.service;

import com.liveguard.domain.Question;
import com.liveguard.dto.QuestionDTO;
import org.springframework.data.domain.Page;

public interface QuestionService {

    Page<Question> findAllByChipVersionId(Long id, int pageNum);

    void save(QuestionDTO questionDTO);

    Question findById(Long id);
}
