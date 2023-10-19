package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.entity.Question;
import com.intelliedu.intelliedu.mapper.QuestionMapper;

/**
 * QuestionService
 */
@Service
public class QuestionService extends ContentService<Question, QuestionDto, QuestionMapper> {

  @Override
  protected Question createContent() {
    return new Question(); 
  }
}
