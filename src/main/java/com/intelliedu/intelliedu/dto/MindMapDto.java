package com.intelliedu.intelliedu.dto;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  private String name;

  private MultipartFile data;

  @JsonProperty
  private UserDto userDto;
}
