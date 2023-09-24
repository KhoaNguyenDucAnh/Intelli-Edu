package com.intelliedu.intelliedu.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {

  @Id 
  @GeneratedValue 
  private Long id;

  private String content;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;
}	
