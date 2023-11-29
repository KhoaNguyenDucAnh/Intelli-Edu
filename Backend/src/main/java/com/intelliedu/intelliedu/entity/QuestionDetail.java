package com.intelliedu.intelliedu.entity;

import java.util.UUID;

import com.intelliedu.intelliedu.dto.QuestionDtoDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuestionDetail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

  public void setAnswer(QuestionDtoDetail questionDtoDetail) {
    correctAnswer = questionDtoDetail.getAnswer().get(0);
    incorrectAnswer1 = questionDtoDetail.getAnswer().get(1);
    incorrectAnswer2 = questionDtoDetail.getAnswer().get(2);
    incorrectAnswer3 = questionDtoDetail.getAnswer().get(3);
  }
}
