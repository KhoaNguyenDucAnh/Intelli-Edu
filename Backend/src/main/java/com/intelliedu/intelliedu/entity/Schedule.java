package com.intelliedu.intelliedu.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Schedule
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Schedule {

  @Id 
  @GeneratedValue 
  private Long id;

  @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Event> event;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;
}
