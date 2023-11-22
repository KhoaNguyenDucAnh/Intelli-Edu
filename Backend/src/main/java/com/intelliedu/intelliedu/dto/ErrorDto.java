package com.intelliedu.intelliedu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {

  private int status;
  
  private String error;
  
  private String message;
}
