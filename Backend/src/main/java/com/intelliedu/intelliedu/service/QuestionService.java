package com.intelliedu.intelliedu.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.entity.Question;
import com.intelliedu.intelliedu.entity.QuestionDetail;
import com.intelliedu.intelliedu.exception.NotFoundException;
import com.intelliedu.intelliedu.mapper.QuestionMapper;

/**
 * QuestionService
 */
@Service
public class QuestionService extends ContentService<Question, QuestionDto> {

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private AIService aiService;

  @Override
  protected Question createContent(String title) {
    return Question.builder().content(new ArrayList<>()).build(); 
  }

  @Override
  protected Class<Question> getGenericClass() {
    return Question.class; 
  }

  /*public QuestionDto createQuestion(UUID id, QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    Question question = findContentHelper(id, authentication);
    question.getContent().add(questionMapper.toQuestionDetail(questionDtoDetail));
    return saveContent(question);
  }

  public QuestionDto updateQuestion(UUID id, UUID questionId, QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    Question question = findContentHelper(id, authentication);
    QuestionDetail questionDetail = question.getContent().stream().filter(q -> questionId.equals(q.getId())).findFirst().orElseThrow(() -> new NotFoundException(QuestionDetail.class, questionId));
    question.getContent().set(question.getContent().indexOf(questionDetail), questionMapper.toQuestionDetail(questionDtoDetail));
    return saveContent(question);
  }*/

  public boolean checkQuestion(UUID id, UUID questionId, String answer, Authentication authentication) {
    return findContentHelper(id, authentication).getContent().stream().filter(q -> questionId.equals(q.getId())).findFirst().orElseThrow(() -> new NotFoundException(QuestionDetail.class, questionId)).getCorrectAnswer().equals(answer);
  }
}
