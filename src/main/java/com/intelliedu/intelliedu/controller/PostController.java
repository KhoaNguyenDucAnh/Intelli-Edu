package com.intelliedu.intelliedu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.service.PostService;

import jakarta.validation.Valid;

/**
 * PostController
 */
@RestController
@RequestMapping("/api/v1/post")
@ResponseStatus(code = HttpStatus.OK)
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping("/find/{query}")
  public Map<String, Page<PostDto>> findPost(@PathVariable(value = "query") String query, Authentication authentication, Pageable pageable) {
    return postService.findPost(query, authentication, pageable);
  }

  @GetMapping("/id/{id}")
  public PostDto findPost(@PathVariable(value = "id") Long id, Authentication authentication) {
    return postService.findPost(id, authentication);
  }

  @PostMapping("/")
  public void addPost(@RequestBody @Valid PostDto postDto, Authentication authentication) {
    postService.createPost(postDto, authentication);
  }

  @PutMapping("/{id}")
  public void updatePost(@PathVariable(value = "id") Long id, @RequestBody @Valid PostDto postDto, Authentication authentication) {
    postService.updatePost(id, postDto, authentication);
  }

  @PutMapping("/answer")
  public void setAnswer(@RequestParam(required = true) Long postId, @RequestParam(required = true) Long commentId) {
    postService.setAnswer(postId, commentId);
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable(value = "id") Long id, Authentication authentication) {
    postService.deletePost(id, authentication);
  }
}
