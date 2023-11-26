package com.intelliedu.intelliedu.controller;

import java.util.Map;

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

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.service.CommentService;

/**
 * CommentController
 */
//@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

	@GetMapping("/{postId}")
	public Map<String, Page<CommentDto>> findComment(@PathVariable String postId, Authentication authentication, Pageable pageable) {
		return commentService.findComment(postId, authentication, pageable);
	}

  @PostMapping("/{postId}")
  public CommentDto createComment(@PathVariable String postId, @RequestBody CommentDto commentDto, Authentication authentication) {
    return commentService.createComment(postId, commentDto, authentication);
  }

  @PutMapping("/{id}")
  public CommentDto updateComment(@PathVariable String id, @RequestBody CommentDto commentDto, Authentication authentication) {
    return commentService.updateComment(id, commentDto, authentication);
  }

  @DeleteMapping("/{id}")
  public void deleteComment(@PathVariable String id, Authentication authentication) {
    commentService.deleteComment(id, authentication);
  }
}
