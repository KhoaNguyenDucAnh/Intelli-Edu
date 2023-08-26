package com.intelliedu.intelliedu.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * PostDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostDto {

	private Long id;

	private ZonedDateTime createdAt;

	private ZonedDateTime lastOpened;

	private Integer upvote;

  private Integer downvote;
}
