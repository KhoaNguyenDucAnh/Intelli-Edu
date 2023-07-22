package com.intelliedu.intelliedu.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MindMapDto {

  private Long id;

  private String title;

  private Map<String, Object> data;
}
