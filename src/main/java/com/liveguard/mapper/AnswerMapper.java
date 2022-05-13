package com.liveguard.mapper;

import com.liveguard.domain.Answer;
import com.liveguard.dto.AnswerDTO;
import com.liveguard.dto.SimpleUserDTO;

import java.util.ArrayList;
import java.util.List;

public class AnswerMapper {

    public static Answer answerDTOTOAnswer(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setAnswerContent(answerDTO.getAnswerContent());

        return answer;
    }

    public static AnswerDTO answerTOAnswerDTO(Answer answer) {
        SimpleUserDTO user = new SimpleUserDTO(answer.getUser().getId(),
                answer.getUser().getEmail(), answer.getUser().getName(),
                answer.getUser().getAbout(), answer.getUser().getAvatar());

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setAnswerContent(answer.getAnswerContent());
        answerDTO.setApproved(answer.isApproved());
        answerDTO.setAnswerTime(answer.getAnswerTime());
        answerDTO.setQuestionId(answer.getQuestion().getId());
        answerDTO.setUser(user);

        return answerDTO;
    }
}
