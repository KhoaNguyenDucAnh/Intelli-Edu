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
  private PostRepo postRepo;

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private AuthService authService;

  public CommentDto createComment(Long postId, CommentDto commentDto, Authentication authentication) {
    Post post = postRepo.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    
    Account account = authService.getAccount(authentication);

    Comment comment = commentMapper.toComment(commentDto);

    comment.setAccount(account);
    comment.setPost(post);

    return commentMapper.toCommentDto(commentRepo.save(comment));
  }

  public CommentDto updateComment(CommentDto commentDto, Authentication authentication) {
    if (commentRepo.existsByIdAndAccountId(commentDto.getId(), authService.getAccount(authentication).getId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return commentMapper.toCommentDto(commentRepo.save(commentMapper.toComment(commentDto)));
  }

  public void setAnswer(Long commentId, Long postId) {
    if (!commentRepo.existsByIdAndPostId(commentId, postId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    Post post = postRepo.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

    comment.setIsAnswer(true);
    post.setIsAnswered(true);

    commentRepo.save(comment);
    postRepo.save(post);
  }

  public void deleteComment(Long id, Authentication authentication) {
    commentRepo.deleteByIdAndAccount(id, authService.getAccount(authentication)); 
  }
}
