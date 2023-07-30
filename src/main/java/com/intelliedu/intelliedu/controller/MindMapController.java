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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.service.MindMapService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mindmap")
@ResponseStatus(code = HttpStatus.OK)
public class MindMapController {

  @Autowired
  private MindMapService mindMapService;

  @GetMapping("/find/{query}")
  public Map<String, Page<MindMapDto>> findMindMap(@PathVariable(value = "query") String query, Authentication authentication, Pageable pageable) {
    return mindMapService.findMindMap(query, authentication, pageable);
  }

  @GetMapping("/id/{id}")
  public MindMapDto findMindMap(@PathVariable(value = "id") Long id, Authentication authentication) {
    return mindMapService.findMindMap(id, authentication);
  }

  @PostMapping("/")
  public MindMapDto createMindMap(@RequestBody @Valid MindMapDto mindMapDto, Authentication authentication) {
    return mindMapService.createMindMap(mindMapDto, authentication);
  }

  @PutMapping("/")
  public MindMapDto updateMindMap(@RequestBody @Valid MindMapDto mindMapDto, Authentication authentication) {
    return mindMapService.updateMindMap(mindMapDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteMindMap(@PathVariable(value = "id") Long id, Authentication authentication) {
    mindMapService.deleteMindMap(id, authentication);
  }
}
