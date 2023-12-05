package com.intelliedu.intelliedu.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.config.VoteStatus;
import com.intelliedu.intelliedu.service.PostService;

/**
 * PostController
 */
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	private PostService postService;

	@PutMapping("/upvote/{id}")
	public Integer upvote(@PathVariable UUID id, Authentication authentication) {
		return postService.vote(id, VoteStatus.UPVOTE, authentication);
	}

	@PutMapping("/downvote/{id}")
	public Integer downvote(@PathVariable UUID id, Authentication authentication) {
		return postService.vote(id, VoteStatus.DOWNVOTE, authentication);
	}
}
