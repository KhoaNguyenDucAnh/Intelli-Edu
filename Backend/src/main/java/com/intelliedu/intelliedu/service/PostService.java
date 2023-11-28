package com.intelliedu.intelliedu.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.VoteStatus;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Post;
import com.intelliedu.intelliedu.entity.Vote;
import com.intelliedu.intelliedu.entity.VoteId;
import com.intelliedu.intelliedu.repository.PostRepo;
import com.intelliedu.intelliedu.repository.VoteRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * PostService
 */
@Service
public class PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private VoteRepo voteRepo;

	@Autowired
	private AuthService authService;

	public Long vote(UUID id, VoteStatus voteStatus, Authentication authentication) {
		Post post = postRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Account account = authService.getAccount(authentication);
		
		voteRepo.findById(new VoteId(post.getId(), account.getId())).ifPresentOrElse(
			(vote) -> {
				vote.setVoteStatus(vote.getVoteStatus() == voteStatus ? VoteStatus.NEUTRAL : voteStatus);
				voteRepo.save(vote);
			},
			() -> voteRepo.save(new Vote(new VoteId(post.getId(), account.getId()), post, account, voteStatus))
		);

		return voteRepo.countByVoteStatus(VoteStatus.UPVOTE) - voteRepo.countByVoteStatus(VoteStatus.DOWNVOTE); 
	}
}
