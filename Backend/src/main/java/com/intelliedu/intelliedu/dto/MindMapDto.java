package com.intelliedu.intelliedu.dto;

import java.util.Map;

import com.intelliedu.intelliedu.config.Subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MindMapDto {

	private PostDto postDto;

	private String title;

  private Subject subject;

  private Map<String, Object> content;
}
