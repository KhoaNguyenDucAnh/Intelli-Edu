package com.intelliedu.intelliedu.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelliedu.intelliedu.config.EventType;

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
public class EventDto {

	private Long id;

	private String name;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d/M/yyyy")
	private LocalDate date;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime time;
	
	private boolean urgent;

	private boolean important;

	private EventType eventType;

	private String description;

  private boolean shared;
}
