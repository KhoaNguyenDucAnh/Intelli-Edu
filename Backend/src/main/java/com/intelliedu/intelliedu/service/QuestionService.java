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

import jakarta.transaction.Transactional;

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

  public QuestionDto findContent(UUID id, Boolean shuffle, Authentication authentication) {
    return questionMapper.toDto(findContentHelper(id, authentication), shuffle);
  }

  private QuestionDetail findQuestionDetail(UUID questionId, Authentication authentication) {
    return questionDetailRepo
      .findByIdAndParentAccount(questionId, authService.getAccount(authentication))
      .orElseThrow(() -> new NotFoundException(QuestionDetail.class, questionId));
  }

  public QuestionDtoDetail createQuestionDetail(UUID id, Boolean shuffle, QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    Question question = findContentHelper(id, authentication);
    QuestionDetail questionDetail = questionMapper.toQuestionDetail(questionDtoDetail);
    question.addContent(questionDetail);
    saveContent(question);
    return questionMapper.toQuestionDtoDetail(questionDetail, shuffle);
  }

  public QuestionDto updateQuestionDetail(UUID questionId, QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    QuestionDetail questionDetail = findQuestionDetail(questionId, authentication);
    questionDetailRepo.save(questionMapper.toQuestionDetail(questionDtoDetail, questionDetail));
    return questionMapper.toDto(questionDetail.getParent());
  }

  @Transactional
  public void deleteQuestionDetail(UUID questionId, Authentication authentication) {
    questionDetailRepo.deleteByIdAndParentAccount(questionId, authService.getAccount(authentication));
  }

  @Transactional
  public void deleteAllQuestionDetail(UUID id, Authentication authentication) {
    questionDetailRepo.deleteAllByParentIdAndParentAccount(id, authService.getAccount(authentication));
  }

  public boolean checkQuestion(UUID questionId, String answer, Authentication authentication) {
    return findQuestionDetail(questionId, authentication).getCorrectAnswer().equals(answer);
  }
}
