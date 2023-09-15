package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.config.VoteStatus;
import com.intelliedu.intelliedu.entity.Vote;
import com.intelliedu.intelliedu.entity.VoteId;

/**
 * UpvoteDownvoteRepo
 */
public interface VoteRepo extends JpaRepository<Vote, VoteId> {

	Long countByVoteStatus(VoteStatus voteStatus);
}
