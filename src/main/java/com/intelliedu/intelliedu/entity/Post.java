package com.intelliedu.intelliedu.entity;

import java.sql.Timestamp;
import java.util.List;

import com.intelliedu.intelliedu.config.Subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "post")
public class Post {

  @Id 
  @GeneratedValue 
  private Long id;

  @Column(name = "created_at")
  private Timestamp createdAt;

  private Subject subject;

  private String title;

  private String content;

  @Column(name = "is_answered")
  private Boolean isAnswered;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Comment> comment;
}
