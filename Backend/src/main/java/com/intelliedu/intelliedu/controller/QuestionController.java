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

  @GetMapping("/{token}")
  public QuestionDto findQuestion(@PathVariable String token, Authentication authentication) {
    return questionService.findContent(token, authentication);
  }

  @PostMapping("/{fileId}")
  public QuestionDto createQuestion(@PathVariable String token, Authentication authentication) {
    return questionService.createContent(token, authentication);
  }

  @PutMapping("/{token}")
  public QuestionDto updateQuestion(@PathVariable String token, @RequestBody @Valid QuestionDto questionDto, Authentication authentication) {
    return questionService.updateContent(token, questionDto, authentication);
  }

  @DeleteMapping("/{token}")
  public void deleteQuestion(@PathVariable String token, Authentication authentication) {
    questionService.deleteContent(token, authentication);
  }
}
