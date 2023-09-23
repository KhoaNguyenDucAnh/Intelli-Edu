package com.intelliedu.intelliedu.dto;

import java.time.ZonedDateTime;

import com.intelliedu.intelliedu.config.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * FileDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FileDto {

  private Long id;

  private String title;

  private Subject subject;

  private ZonedDateTime createdAt;

	private ZonedDateTime lastOpened;
}
