package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.service.FileService;

import jakarta.validation.Valid;

/**
 * FileController
 */
@RestController
@RequestMapping("/api/v1/file")
@ResponseStatus(code = HttpStatus.OK)
public class FileController {

  @Autowired
  private FileService fileService;

  @GetMapping("")
  public Page<FileDto> findFile(@RequestParam(name = "search",	defaultValue = "") String title, Authentication authentication, Pageable pageable) {
    return fileService.findFile(title, authentication, pageable);
  }

  @GetMapping("/{id}")
  public FileDto findFile(@PathVariable String id, Authentication authentication) {
    return fileService.findFile(id, authentication);
  }

  @PostMapping("")
  public FileDto createFile(@RequestBody @Valid FileDto fileDto, Authentication authentication) {
    return fileService.createFile(fileDto, authentication);
  }
}
