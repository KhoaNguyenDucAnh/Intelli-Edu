package com.intelliedu.intelliedu.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
  @GenericGenerator(name = "UUID", strategy = "com.intelliedu.intelliedu.util.IdGenerator")
  @GeneratedValue(generator = "UUID", strategy = GenerationType.SEQUENCE)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Event event;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;

  @Builder.Default
  private boolean owned = false;
}
