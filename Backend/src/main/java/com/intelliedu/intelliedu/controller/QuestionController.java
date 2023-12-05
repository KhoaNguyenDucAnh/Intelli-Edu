package com.intelliedu.intelliedu.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.dto.QuestionDtoDetail;
import com.intelliedu.intelliedu.service.QuestionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 * QuestionController
 */
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @GetMapping("")
  public Map<String, Page<QuestionDto>> findQuestion(@RequestParam(name = "search",	defaultValue = "") String query, Authentication authentication, Pageable pageable) {
    return questionService.findContent(query, authentication, pageable);
  }

  @GetMapping("/{id}")
  public QuestionDto findQuestion(@PathVariable UUID id, @RequestParam(name = "shuffle", defaultValue = "false") Boolean shuffle, Authentication authentication) {
    return questionService.findContent(id, shuffle, authentication);
  }

  @PostMapping("/{id}")
  public QuestionDto createQuestion(@PathVariable UUID id, Authentication authentication) {
    return questionService.createContent(id, authentication);
  }

  /*@PutMapping("/{id}")
  public QuestionDto updateQuestion(@PathVariable UUID id, @RequestBody @Valid QuestionDto questionDto, Authentication authentication) {
    return questionService.updateContent(id, questionDto, authentication);
  }*/

  @DeleteMapping("/{id}")
  public void deleteQuestion(@PathVariable UUID id, Authentication authentication) {
    questionService.deleteContent(id, authentication);
  }

  @PostMapping("/q/{id}")
  public QuestionDto createQuestionDetail(@PathVariable UUID id, @RequestBody @Valid QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    return questionService.createQuestionDetail(id, questionDtoDetail, authentication);
  }

  @PutMapping("/q/{questionId}")
  public QuestionDto updateQuestionDetail(@PathVariable UUID questionId, @RequestBody @Valid QuestionDtoDetail questionDtoDetail, Authentication authentication) {
    return questionService.updateQuestionDetail(questionId, questionDtoDetail, authentication);
  }

  @DeleteMapping("/q/{questionId}")
  public void deleteQuestionDetail(@PathVariable UUID questionId, Authentication authentication) {
    questionService.deleteQuestionDetail(questionId, authentication);
  }

  @PostMapping("/q/check/{questionId}")
  public Boolean checkQuestion(@PathVariable UUID questionId, @RequestBody @NotEmpty String answer, Authentication authentication) {
    return questionService.checkQuestion(questionId, answer, authentication);
  }
}
