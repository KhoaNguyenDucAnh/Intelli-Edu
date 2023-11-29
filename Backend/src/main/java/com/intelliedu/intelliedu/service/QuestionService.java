package com.intelliedu.intelliedu.service;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.entity.Question;
import com.intelliedu.intelliedu.entity.QuestionDetail;
import com.intelliedu.intelliedu.exception.NotFoundException;

/**
 * QuestionService
 */
@Service
public class QuestionService extends ContentService<Question, QuestionDto> {

  @Override
  protected Question createContent(String title) {
    return Question.builder().content(new HashMap<>()).build(); 
  }

  @Override
  protected Class<Question> getGenericClass() {
    return Question.class; 
  }

  public boolean checkQuestion(UUID id, Integer questionId, String answer, Authentication authentication) {
    Question question = findContentHelper(id, authentication);
    QuestionDetail questionDetail = question.getContent().get(questionId);
    if (questionDetail == null) {
      throw new NotFoundException(QuestionDetail.class, questionId.toString());
    }
    return questionDetail.getCorrectAnswer().equals(answer);
  }
}
