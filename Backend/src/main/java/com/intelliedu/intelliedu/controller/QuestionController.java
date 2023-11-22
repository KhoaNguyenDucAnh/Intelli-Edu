package com.intelliedu.intelliedu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.service.QuestionService;

import jakarta.validation.Valid;

/**
 * QuestionController
 */
@RestController
@RequestMapping("/api/v1/question")
@ResponseStatus(code = HttpStatus.OK)
public class QuestionController {

  @Autowired
  private QuestionService questionService;

  @GetMapping("")
  public Map<String, Page<QuestionDto>> findQuestion(@RequestParam(name = "search",	defaultValue = "") String query, Authentication authentication, Pageable pageable) {
    return questionService.findContent(query, authentication, pageable);
  }

  @PostMapping("/{id}")
  public QuestionDto createQuestion(@PathVariable String id, Authentication authentication) {
    return questionService.createContent(id, authentication);
  }

  @PutMapping("/{id}")
  public QuestionDto updateQuestion(@PathVariable String id, @RequestBody @Valid QuestionDto questionDto, Authentication authentication) {
    return questionService.updateContent(id, questionDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteQuestion(@PathVariable String id, Authentication authentication) {
    questionService.deleteContent(id, authentication);
  }

  @PutMapping("/share/{id}")
  public void shareDocument(@PathVariable String id, Authentication authentication) {
    questionService.shareContent(id, authentication);
  }
}
