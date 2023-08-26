package com.intelliedu.intelliedu.dto;

import java.time.ZonedDateTime;

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

	private ZonedDateTime deadline;
	
	private Boolean urgent;

	private Boolean important;

	private EventType eventType;

	private String description;
}
