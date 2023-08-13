package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.config.CommentType;
import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.service.CommentService;

/**
 * CommentController
 */
@RestController
@RequestMapping("/api/v1/comment")
@ResponseStatus(code = HttpStatus.OK)
public class CommentController {

  @Autowired
  private CommentService commentService;

  @PostMapping("")
  public CommentDto createComment(
		@RequestParam(name = "type", required = true) CommentType commentType, 
		@RequestParam(name = "id", required = true) Long id, 
		@RequestBody CommentDto commentDto, 
		Authentication authentication
	) {
    return commentService.createComment(commentType, id, commentDto, authentication);
  }

  @PutMapping("/")
  public CommentDto updateComment(@RequestBody CommentDto commentDto, Authentication authentication) {
    return commentService.updateComment(commentDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable(value = "id") Long id, Authentication authentication) {
    commentService.deleteComment(id, authentication);
  }
}
