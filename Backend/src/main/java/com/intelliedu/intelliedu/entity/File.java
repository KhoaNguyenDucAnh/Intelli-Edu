package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.intelliedu.intelliedu.config.Subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * File
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class File {

  @Id
  @GenericGenerator(name = "UUID", strategy = "com.intelliedu.intelliedu.util.IdGenerator")
  @GeneratedValue(generator = "UUID", strategy = GenerationType.SEQUENCE)
  private String id;

  private String title;

  @Enumerated(EnumType.STRING)
  private Subject subject;

  @CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime lastOpened;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;

  public void setAccount(Account account) {
    this.account = account;
    account.getFile().add(this);
  }
}
