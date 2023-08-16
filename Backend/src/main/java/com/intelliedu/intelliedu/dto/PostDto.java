package com.intelliedu.intelliedu.dto;

import java.sql.Timestamp;

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

	private Timestamp createdAt;

	private Timestamp lastOpened;

	private Integer upvote;

  private Integer downvote;
}
