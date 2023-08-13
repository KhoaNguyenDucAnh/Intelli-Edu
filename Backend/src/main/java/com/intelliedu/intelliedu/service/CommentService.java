package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.CommentType;
import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.mapper.CommentMapper;
import com.intelliedu.intelliedu.repository.CommentRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/** CommentServiceImpl */
@Service
public class CommentService {

	//@Autowired
	//private PostRepo postRepo;

	@Autowired
  private CommentRepo commentRepo;

  @Autowired
  private CommentMapper commentMapper;

  @Autowired
  private AuthService authService;
	
  public CommentDto createComment(CommentType commentType, Long parentId, CommentDto commentDto, Authentication authentication) {
		return null;
	}

  public CommentDto updateComment(CommentDto commentDto, Authentication authentication) {
    if (commentRepo.existsByIdAndAccountId(commentDto.getId(), authService.getAccount(authentication).getId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

		return commentMapper.toCommentDto(commentRepo.save(commentMapper.toComment(commentDto)));
	}

  public void deleteComment(Long id, Authentication authentication) {
    commentRepo.deleteByIdAndAccount(id, authService.getAccount(authentication)); 
  }
}
