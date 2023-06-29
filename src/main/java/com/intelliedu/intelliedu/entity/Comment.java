package com.intelliedu.intelliedu.entity;

import java.time.OffsetDateTime;
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
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "date_time")
  private OffsetDateTime dateTime;

  @Column(name = "content")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post")
  private Post post;
}
