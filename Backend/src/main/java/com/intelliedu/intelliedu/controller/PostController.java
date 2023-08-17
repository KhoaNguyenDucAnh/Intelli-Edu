package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.config.VoteStatus;
import com.intelliedu.intelliedu.service.PostService;

/**
 * PostController
 */
@RestController
@RequestMapping("/api/v1/post")
@ResponseStatus(code = HttpStatus.OK)
public class PostController {

	@Autowired
	private PostService postService;

	@PutMapping("/upvote/{id}")
	public Long upvote(@PathVariable Long id, Authentication authentication) {
		return postService.vote(id, VoteStatus.UPVOTE, authentication);
	}

	@PutMapping("/downvote/{id}")
	public Long downvote(@PathVariable Long id, Authentication authentication) {
		return postService.vote(id, VoteStatus.DOWNVOTE, authentication);
	}
}
