package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.entity.Question;

/**
 * QuestionService
 */
@Service
public class QuestionService extends ContentService<Question, QuestionDto> {

  @Override
  protected Question createContent(String title) {
    return Question.builder().content(Map.of()).build(); 
  }

  @Override
  protected Class<Question> getGenericClass() {
    return Question.class; 
  }
}
