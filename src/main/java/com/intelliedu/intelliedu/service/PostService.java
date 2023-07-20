package com.intelliedu.intelliedu.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.dto.PostDto;

/**
 * PostService
 */
public interface PostService {

  public List<PostDto> findPost(String query);

  public List<PostDto> findPost(String query, Authentication authentication);

  public void createPost(PostDto postDto, Authentication authentication);

  public void updatePost(String title, PostDto postDto, Authentication authentication);

  public void setAnswer(String commentRawId, String postRawId);

  public void deletePost(String title, Authentication authentication);
}

