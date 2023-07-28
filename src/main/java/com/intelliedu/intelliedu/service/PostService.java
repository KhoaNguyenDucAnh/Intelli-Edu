package com.intelliedu.intelliedu.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;
import com.intelliedu.intelliedu.entity.Post;
import com.intelliedu.intelliedu.mapper.PostMapper;
import com.intelliedu.intelliedu.repository.CommentRepo;
import com.intelliedu.intelliedu.repository.PostRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * PostServiceImpl
 */
@Service
public class PostService {

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private PostMapper postMapper;

  @Autowired
  private AuthService authService;

  @Autowired
  private CommentRepo commentRepo;

  public Map<String, Page<PostDto>> findPost(String query, Authentication authentication, Pageable pageable) {
    if (authentication == null) {
      return Map.of("other", postMapper.toPostDto(postRepo.findByTitleOrContent(query, query, pageable))); 
    } else {
      Long accountId = authService.getAccount(authentication).getId();
      return Map.of(
        "other", postMapper.toPostDto(postRepo.findByTitleOrContentAndAccountIdIsNot(query, query, accountId, pageable)),
        "own", postMapper.toPostDto(postRepo.findByTitleOrContentAndAccountId(query, query, accountId, pageable))
      );
    }
  }

  public PostDto findPost(Long id, Authentication authentication) {
    return postMapper.toPostDto(postRepo
      .findByIdAndAccount(id, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }

  public void createPost(PostDto postDto, Authentication authentication) {
    Post post = postMapper.toPost(postDto);

    Account account = authService.getAccount(authentication);

    // Add post to account
    List<Post> accountPost = account.getPost();

    if (accountPost == null) {
      accountPost = new ArrayList<>();
    }

    if (accountPost.stream().anyMatch(tempPost -> tempPost.getTitle().equals(post.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    
    accountPost.add(post);

    // Set attributes
    post.setIsAnswered(false);
    post.setDateTime(OffsetDateTime.now());
    post.setAccount(account);

    postRepo.save(post);
  }

  public void updatePost(PostDto postDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    postRepo
      .findByIdAndAccount(postDto.getId(), account)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (account.getPost().stream().anyMatch(tempPost -> tempPost.getTitle().equals(postDto.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    postRepo.save(postMapper.toPost(postDto));
  }

  public void setAnswer(Long commentId, Long postId) {
    if (postRepo.existCommentWithId(commentId, postId) == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    Post post = postRepo.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));  
    Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    post.setIsAnswered(true);
    comment.setIsAnswer(true);

    postRepo.save(post);
    commentRepo.save(comment);
  }

  public void deletePost(Long id, Authentication authentication) {
    postRepo.deleteByIdAndAccount(id, authService.getAccount(authentication)); 
  }
}
