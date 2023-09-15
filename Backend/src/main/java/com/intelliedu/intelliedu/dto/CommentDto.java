package com.intelliedu.intelliedu.dto;

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

	private PostDto postDto;
  
	private String content;
}
