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

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.service.DocumentService;

import jakarta.validation.Valid;

/**
 * DocumentController
 */
@RestController
@RequestMapping("/api/v1/document")
@ResponseStatus(code = HttpStatus.OK)
public class DocumentController {

	@Autowired
  private DocumentService documentService;

  @GetMapping("")
  public Map<String, Page<DocumentDto>> findDocument(@RequestParam(name = "search",	defaultValue = "") String query, Authentication authentication, Pageable pageable) {
    return documentService.findContent(query, authentication, pageable);
  }

  @PostMapping("/{id}")
  public DocumentDto createDocument(@PathVariable String id, Authentication authentication) {
    return documentService.createContent(id, authentication);
  }

  @PutMapping("/{id}")
  public DocumentDto updateDocument(@PathVariable String id, @RequestBody @Valid DocumentDto documentDto, Authentication authentication) {
    return documentService.updateContent(id, documentDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteDocument(@PathVariable String id, Authentication authentication) {
    documentService.deleteContent(id, authentication);
  }

  @PutMapping("/share/{id}")
  public void shareDocument(@PathVariable String id, Authentication authentication) {
    documentService.shareContent(id, authentication);
  }
}
