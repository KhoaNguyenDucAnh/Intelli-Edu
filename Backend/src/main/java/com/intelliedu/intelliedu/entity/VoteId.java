package com.intelliedu.intelliedu.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class VoteId implements Serializable {

	private String postId;

	private String accountId;
}
