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

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.service.MindMapService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mindmap")
public class MindMapController {

  @Autowired
  private MindMapService mindMapService;

  @GetMapping("")
  public Map<String, Page<MindMapDto>> findMindMap(@RequestParam(name = "search",	defaultValue = "") String query, Authentication authentication, Pageable pageable) {
    return mindMapService.findContent(query, authentication, pageable);
  }

  @GetMapping("/{id}")
  public MindMapDto findMindMap(@PathVariable UUID id, Authentication authentication) {
    return mindMapService.findContent(id, authentication);
  }

  @PostMapping("/{id}")
  public MindMapDto createMindMap(@PathVariable UUID id, Authentication authentication) {
    return mindMapService.createContent(id, authentication);
  }

  @PutMapping("/{id}")
  public MindMapDto updateMindMap(@PathVariable UUID id, @RequestBody @Valid MindMapDto mindMapDto, Authentication authentication) {
    return mindMapService.updateContent(id, mindMapDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteMindMap(@PathVariable UUID id, Authentication authentication) {
    mindMapService.deleteContent(id, authentication);
  }
}
