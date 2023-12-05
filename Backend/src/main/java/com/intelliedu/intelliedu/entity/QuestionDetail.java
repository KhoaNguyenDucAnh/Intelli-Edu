package com.intelliedu.intelliedu.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuestionDetail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
public class QuestionDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String question;

  private String correctAnswer;

  @ElementCollection
  private List<String> incorrectAnswer;

  @ManyToOne(fetch = FetchType.LAZY)
  private Question parent;
}
