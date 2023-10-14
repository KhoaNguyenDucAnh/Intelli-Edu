package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;

import com.intelliedu.intelliedu.config.EventType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

	@Id
  @GeneratedValue
  private Long id;

	private String name;

	private ZonedDateTime deadline;
	
	private Boolean urgent;

	private Boolean important;

	@Enumerated(EnumType.STRING)	
	private EventType eventType;

	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
  private Schedule schedule;
}
