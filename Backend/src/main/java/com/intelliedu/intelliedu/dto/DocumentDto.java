package com.intelliedu.intelliedu.dto;

import com.intelliedu.intelliedu.config.Subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DocumentDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {

	private PostDto postDto;

	private String title;

  private Subject subject;
	
	private String content;
}
