package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.mapper.PostMapper;
import com.intelliedu.intelliedu.repository.PostRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * PostService
 */
@Service
public class PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private AuthService authService;

	public PostDto findPost(Long id, Authentication authentication) {
		return postMapper.toPostDto(postRepo.findByIdAndAccount(id, authService.getAccount(authentication)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}
}
