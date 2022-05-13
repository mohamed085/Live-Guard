package com.liveguard.mapper;

import com.liveguard.domain.Question;
import com.liveguard.dto.AnswerDTO;
import com.liveguard.dto.QuestionDTO;
import com.liveguard.dto.SimpleChipVersion;
import com.liveguard.dto.SimpleUserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuestionMapper {
    
    public static Question questionDTOToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionContent(questionDTO.getQuestionContent());
        question.setAskTime(LocalDateTime.now());

        return question;
    }

    public static QuestionDTO questionTOQuestionDTO(Question question) {
        List<AnswerDTO> answers = new ArrayList<>();
        question.getAnswers().forEach(answer -> {
            answers.add(AnswerMapper.answerTOAnswerDTO(answer));
        });

        SimpleUserDTO user = new SimpleUserDTO(question.getUser().getId(),
                question.getUser().getEmail(), question.getUser().getName(),
                question.getUser().getAbout(), question.getUser().getAvatar());

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setQuestionContent(question.getQuestionContent());
        questionDTO.setApproved(question.isApproved());
        questionDTO.setAskTime(question.getAskTime());
        questionDTO.setUser(user);
        questionDTO.setChipVersionId(question.getChipVersion().getId());
        questionDTO.setAnswers(answers);

        return questionDTO;
    }
}
