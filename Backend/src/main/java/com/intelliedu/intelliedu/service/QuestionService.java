package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.entity.Question;

/**
 * QuestionService
 */
@Service
public class QuestionService extends ContentService<Question, QuestionDto> {

  @Override
  protected Question createContent() {
    return new Question(); 
  }
}
