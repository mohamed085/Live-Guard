package com.liveguard.service.serviceImp;

import com.liveguard.domain.Answer;
import com.liveguard.domain.Question;
import com.liveguard.domain.User;
import com.liveguard.dto.AnswerDTO;
import com.liveguard.mapper.AnswerMapper;
import com.liveguard.repository.AnswerRepository;
import com.liveguard.service.AccountService;
import com.liveguard.service.AnswerService;
import com.liveguard.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AnswerServiceImp implements AnswerService {


    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final AccountService accountService;

    public AnswerServiceImp(AnswerRepository answerRepository, QuestionService questionService, AccountService accountService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        this.accountService = accountService;
    }

    @Override
    public void save(AnswerDTO answerDTO) {
        log.debug("AnswerService | save | answerDTO:" + answerDTO.toString());
        Answer answer = AnswerMapper.answerDTOTOAnswer(answerDTO);

        User user = accountService.getAuthenticatedAccount();
        log.debug("AnswerService | save | user: " + user.toString());

        Question question = questionService.findById(answerDTO.getQuestionId());
        log.debug("AnswerService | save | question: " + question.toString());

        answer.setApproved(true);
        answer.setAnswerTime(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setUser(user);

        answerRepository.save(answer);
    }
}
