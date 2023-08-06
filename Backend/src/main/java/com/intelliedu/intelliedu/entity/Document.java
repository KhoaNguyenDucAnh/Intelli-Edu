package com.intelliedu.intelliedu.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "document")
public class Document {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "last_opened")
  private Timestamp lastOpened;

  private String data;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;
}
