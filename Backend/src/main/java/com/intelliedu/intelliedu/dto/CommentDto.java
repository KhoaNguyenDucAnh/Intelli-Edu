package com.intelliedu.intelliedu.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
 
  private UUID id;

	private String content;
}
