package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.repository.DocumentRepo;
import com.intelliedu.intelliedu.repository.MindMapRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

@Service
public class MindMapService extends ContentService<MindMap, MindMapDto> {

  @Autowired
  private MindMapRepo mindMapRepo;

  @Autowired
  private DocumentRepo documentRepo;

  @Autowired
  private AuthService authService;

  @Autowired
  private AIService aiService;

  @Override
  protected MindMap createContent(String title) {
    return MindMap.builder().content(Map.of("title", title)).build();
  }

  public String checkMindMap(String id, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    Document document = documentRepo.findByIdAndAccount(id, account).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    MindMap mindMap = mindMapRepo.findByIdAndAccount(id, account).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    try {
      return aiService.request(document, mindMap);
    } catch (JsonProcessingException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
} 
