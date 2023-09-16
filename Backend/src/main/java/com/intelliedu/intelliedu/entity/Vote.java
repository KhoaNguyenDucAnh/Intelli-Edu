package com.intelliedu.intelliedu.entity;

import com.intelliedu.intelliedu.config.VoteStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpvoteDownvote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vote {

	@EmbeddedId
	private VoteId voteId;

	@MapsId("postId")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Post post;

	@MapsId("accountId")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Account account;

	@Enumerated(EnumType.STRING)
	private VoteStatus voteStatus;
}
