package com.intelliedu.intelliedu.entity;

import java.util.List;
import java.util.UUID;

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

  private String incorrectAnswer1;
  
  private String incorrectAnswer2;

  private String incorrectAnswer3;

  @ManyToOne(fetch = FetchType.LAZY)
  private Question parent;

  public void setAnswers(List<String> answer) {
    correctAnswer = answer.get(0);
    incorrectAnswer1 = answer.get(1);
    incorrectAnswer2 = answer.get(2);
    incorrectAnswer3 = answer.get(3);
  }
}
