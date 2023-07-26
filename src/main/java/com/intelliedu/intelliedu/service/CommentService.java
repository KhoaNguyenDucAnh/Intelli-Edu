package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
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
@Service
public class CommentService {

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private AuthService authService;

  @Autowired
  private PostRepo postRepo;

  public void createComment(Long postId, CommentDto commentDto, Authentication authentication) {
    Post post = postRepo.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
    Account account = authService.getAccount(authentication);

    Comment comment = commentMapper.toComment(commentDto);

    comment.setAccount(account);
    comment.setPost(post);

    commentRepo.save(comment);
  }

  public void updateComment(CommentDto commentDto, Authentication authentication) {
    
  }

  public void deleteComment(Long id, Authentication authentication) {
    commentRepo.deleteByIdAndAccountEmail(id, authService.getEmail(authentication)); 
  }
}
