package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.intelliedu.intelliedu.config.Subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * File
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class File {

  @Id
  private String id;

  private String title;

  @Enumerated(EnumType.STRING)
  private Subject subject;

  @CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime lastOpened;
  
  @OneToOne(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Document document;

  @OneToOne(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private MindMap mindMap;

  @OneToOne(mappedBy = "file", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Question question;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;
}
