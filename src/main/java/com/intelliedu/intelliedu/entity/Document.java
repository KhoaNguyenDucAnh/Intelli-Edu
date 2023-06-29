package com.intelliedu.intelliedu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String title;

  @Column(name = "data")
  private String data;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account")
  private Account account;
}
