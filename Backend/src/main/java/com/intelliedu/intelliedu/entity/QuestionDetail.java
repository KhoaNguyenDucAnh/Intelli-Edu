package com.intelliedu.intelliedu.entity;

import java.util.UUID;

import com.intelliedu.intelliedu.dto.QuestionDtoDetail;

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

  private String questionDetail;

  private String correctAnswer;

  private String incorrectAnswer1;
  
  private String incorrectAnswer2;

  private String incorrectAnswer3;

  @ManyToOne(fetch = FetchType.LAZY)
  private Question question;

  public void setAnswer(QuestionDtoDetail questionDtoDetail) {
    correctAnswer = questionDtoDetail.getAnswers().get(0);
    incorrectAnswer1 = questionDtoDetail.getAnswers().get(1);
    incorrectAnswer2 = questionDtoDetail.getAnswers().get(2);
    incorrectAnswer3 = questionDtoDetail.getAnswers().get(3);
  }
}
