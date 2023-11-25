package com.intelliedu.intelliedu.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.intelliedu.intelliedu.config.EventType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	private LocalDate date;

	private LocalTime time;
	
	private boolean urgent;

	private boolean important;

	@Enumerated(EnumType.STRING)	
	private EventType eventType;

	private String description;

  @Builder.Default
  private boolean shared = false;

  @Builder.Default
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Schedule> schedule = new ArrayList<>();

  public void addSchedule(Schedule schedule) {
    this.schedule.add(schedule);
    schedule.setEvent(this);
  }
}
