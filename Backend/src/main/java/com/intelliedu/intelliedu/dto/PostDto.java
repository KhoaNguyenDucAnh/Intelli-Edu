package com.intelliedu.intelliedu.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PostDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

  private UUID id;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastOpened;

	private Integer upvote;

  private Integer downvote;
}
