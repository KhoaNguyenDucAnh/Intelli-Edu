package com.intelliedu.intelliedu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public List<MindMapDto> findMindMap(@PathVariable(value = "query") String query) {
    return mindMapService.findMindMap(query);
  }

  @GetMapping("")
  public List<MindMapDto> findMindMap(Authentication authentication) {
    return mindMapService.findMindMap(authentication);
  }

  @GetMapping("/title/{title}")
  public ResponseEntity<ByteArrayResource> findMindMap(@PathVariable(value = "title") String title, Authentication authentication) {
    return mindMapService.findMindMap(title, authentication);
  }

  @PostMapping("/")
  public void addMindMap(@RequestBody @Valid MindMapDto mindMapDto, Authentication authentication) {
    mindMapService.createMindMap(mindMapDto, authentication);
  }

  @PutMapping("/title{title}")
  public void updateMindMap(@PathVariable(value = "title") String title, @Valid @RequestBody MindMapDto mindMapDto, Authentication authentication) {
    mindMapService.updateMindMap(title, mindMapDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteMindMap(@PathVariable(value = "id") String rawId, Authentication authentication) {
    mindMapService.deleteMindMap(rawId, authentication);
  }
}
