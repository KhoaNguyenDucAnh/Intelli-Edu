package com.intelliedu.intelliedu.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class FileDto {

  private UUID id;

  @NotEmpty(message = "Title must not be empty")
  private String title;

  private ZonedDateTime createdAt;

	private ZonedDateTime lastOpened;

  private DocumentDto document;

  private MindMapDto mindMap;

  private QuestionDto question;
}
