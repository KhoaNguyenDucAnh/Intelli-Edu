package com.intelliedu.intelliedu.dto;

import com.intelliedu.intelliedu.config.Subject;

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

  private Long id;

  private String title;

  private Subject subject;

  private String content;
}
