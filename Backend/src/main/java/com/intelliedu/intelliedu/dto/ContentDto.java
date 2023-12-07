package com.intelliedu.intelliedu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * ContentDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContentDto {

  private boolean shared;
  
  private PostDto post;
}
