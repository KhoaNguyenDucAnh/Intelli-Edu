package com.intelliedu.intelliedu.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intelliedu.intelliedu.config.EventType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EventDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class EventDto {

	private UUID id;

  @NotEmpty(message = "Name must not be empty")
	private String name;

  @NotEmpty(message = "Date must not be empty")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
	private LocalDate date;

  @NotEmpty(message = "Time must not be empty")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime time;
	
  @NotNull
	private boolean urgent;

  @NotNull
	private boolean important;

	private EventType eventType;

	private String description;

  private boolean shared;
}
