package com.intelliedu.intelliedu.dto;

import org.springframework.web.multipart.MultipartFile;

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

  private MultipartFile data;
}
