package com.intelliedu.intelliedu.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonRawValue;

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
public class MindMapDto extends ContentDto {

  @JsonRawValue
  private Map<String, Object> content;
}
