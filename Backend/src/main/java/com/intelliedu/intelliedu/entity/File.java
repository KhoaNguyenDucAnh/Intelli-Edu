package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
 * File
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;

  @CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime lastOpened;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  public void setAccount(Account account) {
    this.account = account;
    account.getFile().add(this);
  }
}
