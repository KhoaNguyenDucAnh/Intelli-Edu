package com.intelliedu.intelliedu.dto;

import java.util.Map;

import com.intelliedu.intelliedu.config.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class MindMapDto extends PostDto {

	private String title;

  private Subject subject;

  private Map<String, Object> content;
}
