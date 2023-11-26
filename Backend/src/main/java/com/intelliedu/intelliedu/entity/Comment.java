package com.intelliedu.intelliedu.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
  @GenericGenerator(name = "UUID", strategy = "com.intelliedu.intelliedu.util.IdGenerator")
  @GeneratedValue(generator = "UUID", strategy = GenerationType.SEQUENCE) 
  private String id;

  private String content;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;
}	
