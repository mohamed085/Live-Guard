package com.liveguard.service.serviceImp;

import com.liveguard.domain.ChipVersion;
import com.liveguard.domain.Question;
import com.liveguard.domain.User;
import com.liveguard.dto.QuestionDTO;
import com.liveguard.exception.BusinessException;
import com.liveguard.mapper.QuestionMapper;
import com.liveguard.repository.QuestionRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.ChipVersionService;
import com.liveguard.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class QuestionServiceImp implements QuestionService {

    public static final int QUESTIONS_PER_PAGE = 20;

    private final QuestionRepository questionRepository;
    private final ChipVersionService chipVersionService;
    private final AccountService accountService;

    public QuestionServiceImp(QuestionRepository questionRepository, ChipVersionService chipVersionService,
                              AccountService accountService) {
        this.questionRepository = questionRepository;
        this.chipVersionService = chipVersionService;
        this.accountService = accountService;
    }

    @Override
    public Page<Question> findAllByChipVersionId(Long id, int pageNum) {
        log.debug("ReviewServices | reviews | reviewDTO: " + id);
        log.debug("ReviewServices | reviews | pageNum: " + pageNum);

        Pageable pageable = PageRequest.of(pageNum - 1 , QUESTIONS_PER_PAGE, Sort.by("askTime").descending());

        return questionRepository.findAllByChipVersionId(id, pageable);
    }

    @Override
    public void save(QuestionDTO questionDTO) {
        log.debug("QuestionService | save | reviewDTO: " + questionDTO.toString());

        User user = accountService.getAuthenticatedAccount();
        log.debug("QuestionService | save | user: " + user.toString());

        ChipVersion chipVersion = chipVersionService.findById(questionDTO.getChipVersionId());
        log.debug("QuestionService | save | chipVersion: " + chipVersion.toString());

        Question question = QuestionMapper.questionDTOToQuestion(questionDTO);
        question.setAskTime(LocalDateTime.now());
        question.setChipVersion(chipVersion);
        question.setUser(user);
        question.setApproved(true);

        questionRepository.save(question);
    }

    @Override
    public Question findById(Long id) {
        log.debug("QuestionService | findById | id: " + id);

        return questionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Question not found", HttpStatus.NOT_FOUND));
    }

}
