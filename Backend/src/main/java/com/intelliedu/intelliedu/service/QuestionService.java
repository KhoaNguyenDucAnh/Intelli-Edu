package com.intelliedu.intelliedu.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.dto.QuestionDtoDetail;
import com.intelliedu.intelliedu.entity.Question;
import com.intelliedu.intelliedu.entity.QuestionDetail;
import com.intelliedu.intelliedu.exception.NotFoundException;
import com.intelliedu.intelliedu.mapper.QuestionMapper;
import com.intelliedu.intelliedu.repository.QuestionDetailRepo;

/**
 * QuestionService
 */
@Service
public class QuestionService extends ContentService<Question, QuestionDto> {

  @Autowired
  private QuestionMapper questionMapper;

  @Autowired
  private QuestionDetailRepo questionDetailRepo;

  @Override
  protected Question createContent(String title) {
    return Question.builder().content(new ArrayList<>()).build(); 
  }

  @Override
  protected Class<Question> getGenericClass() {
    return Question.class; 
  }

  private QuestionDetail findQuestionDetail(UUID id, UUID questionId, Authentication authentication) {
    return questionDetailRepo
      .findByIdAndQuestionIdAndQuestionAccount(id, questionId, authService.getAccount(authentication))
      .orElseThrow(() -> new NotFoundException(QuestionDetail.class, questionId));
  }

  public QuestionDto createQuestionDetail(UUID id, QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    Question question = findContentHelper(id, authentication);
    question.getContent().add(questionMapper.toQuestionDetail(questionDtoDetail));
    return saveContent(question);
  }

  public QuestionDto updateQuestionDetail(UUID id, UUID questionId, QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    QuestionDetail questionDetail = findQuestionDetail(id, questionId, authentication);
    questionDetailRepo.save(questionMapper.toQuestionDetail(questionDtoDetail, questionDetail));
    return findContent(id);
  }

  public boolean checkQuestion(UUID id, UUID questionId, String answer, Authentication authentication) {
    return findQuestionDetail(id, questionId, authentication).getCorrectAnswer().equals(answer);
  }
}
