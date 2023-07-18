package com.intelliedu.intelliedu.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Post;
import com.intelliedu.intelliedu.mapper.PostMapper;
import com.intelliedu.intelliedu.repository.PostRepo;
import com.intelliedu.intelliedu.security.service.AuthService;
import com.intelliedu.intelliedu.service.PostService;

/**
 * PostServiceImpl
 */
public class PostServiceImpl implements PostService {

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private PostMapper postMapper;

  @Autowired
  private AuthService authService;

  @Override
  public List<PostDto> findPost(String query) {
    return postMapper.toPostDto(postRepo.findByTitleAndContent(query, query));
  }

  @Override
  public List<PostDto> findPost(String query, Authentication authentication) {
    return postMapper.toPostDto(postRepo.findByTitleAndContentAndAccount(query, query, authService.getAccount(authentication))); 
  }

  @Override
  public void createPost(PostDto postDto, Authentication authentication) {
    Post post = postMapper.toPost(postDto);

    // Set attributes
    post.setIsAnswered(false);
    post.setDateTime(OffsetDateTime.now());
   
    // Save post to account
    Account account = authService.getAccount(authentication);

    List<Post> accountPost = account.getPost();

    if (accountPost == null) {
      accountPost = new ArrayList<>();
    }

    if (accountPost.stream().anyMatch(tempPost -> tempPost.getTitle().equals(post.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }
    
    accountPost.add(post);

    postRepo.save(post);
  }

  @Override
  public void updatePost(String title, PostDto postDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    Post post = postRepo
        .findByTitleAndAccount(title, account)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    if (account.getPost().stream().anyMatch(tempPost -> tempPost.getTitle().equals(postDto.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    post = postMapper.toPost(postDto);

    postRepo.save(post);
  }


  @Override
  public void setAnswer(CommentDto commentDto) {
       
  }

  @Override
  public void deletePost(String title, Authentication authentication) {
     
  }
}
