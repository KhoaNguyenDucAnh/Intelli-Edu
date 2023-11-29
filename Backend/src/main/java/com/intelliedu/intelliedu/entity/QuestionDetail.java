package com.intelliedu.intelliedu.entity;

import com.intelliedu.intelliedu.dto.QuestionDtoDetail;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class QuestionDetail {

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
