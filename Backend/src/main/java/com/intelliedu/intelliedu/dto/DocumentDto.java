package com.intelliedu.intelliedu.dto;

import com.intelliedu.intelliedu.config.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DocumentDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DocumentDto extends PostDto {

	private String title;

  private Subject subject;
	
	private String content;
}
