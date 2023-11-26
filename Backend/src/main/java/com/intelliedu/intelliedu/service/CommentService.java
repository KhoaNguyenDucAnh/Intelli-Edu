package com.intelliedu.intelliedu.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;
import com.intelliedu.intelliedu.entity.Post;
import com.intelliedu.intelliedu.mapper.CommentMapper;
import com.intelliedu.intelliedu.repository.CommentRepo;
import com.intelliedu.intelliedu.repository.PostRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/** CommentServiceImpl */
//@Service
public class CommentService {

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private AuthService authService;

	public Map<String, Page<CommentDto>> findComment(UUID postId, Authentication authentication, Pageable pageable) {
		if (authentication == null) {
      return Map.of("other", commentMapper.toCommentDto(commentRepo.findByPostId(postId, pageable)));
    } else {
      Account account = authService.getAccount(authentication);
      return Map.of(
        "other", commentMapper.toCommentDto(commentRepo.findByPostIdAndAccountIsNot(postId, account, pageable)),
        "own", commentMapper.toCommentDto(commentRepo.findByPostIdAndAccount(postId, account, pageable))
      );
    }

	}
	
  public CommentDto createComment(UUID postId, CommentDto commentDto, Authentication authentication) {
    Post post = postRepo.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    Comment comment = commentMapper.toComment(commentDto);
    comment.setPost(post);
    comment.setAccount(authService.getAccount(authentication));

    return commentMapper.toCommentDto(commentRepo.save(comment));
  }

  public CommentDto updateComment(UUID id, CommentDto commentDto, Authentication authentication) {
    Comment comment = commentRepo
      .findByIdAndAccount(id, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return commentMapper.toCommentDto(commentRepo.save(commentMapper.toComment(commentDto, comment)));
  }

  @Transactional
  public void deleteComment(UUID id, Authentication authentication) {
    commentRepo.deleteByIdAndAccount(id, authService.getAccount(authentication));
  }
}
