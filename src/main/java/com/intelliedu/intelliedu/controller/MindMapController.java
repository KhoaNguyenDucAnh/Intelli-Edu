package com.intelliedu.intelliedu.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/id/{id}")
  public ResponseEntity<ByteArrayResource> findMindMapById(
      @PathVariable(value = "id") String rawId) {
    return mindMapService.findMindMapById(rawId);
  }

  @GetMapping("/name/{name}")
  public List<MindMapDto> findMindMapByTitle(@PathVariable(value = "name") String title) {
    return mindMapService.findMindMapByTitle(title);
  }

  @PostMapping("/")
  public void addMindMap(@RequestBody @Valid MindMapDto MindMapDto) {
    mindMapService.addMindMap(MindMapDto);
  }

  @PutMapping("/{id}")
  public void updateMindMap(
      @PathVariable(value = "id") String rawId,
      @Valid @RequestBody MindMapDto mindMapDto) {
    mindMapService.updateMindMap(rawId, mindMapDto);
  }

  @DeleteMapping("/{id}")
  public void deleteMindMap(@PathVariable(value = "id") String rawId) {
    mindMapService.deleteMindMap(rawId);
  }
}
