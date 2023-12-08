package com.intelliedu.intelliedu.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuestionDtoDetail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class QuestionDtoDetail {

  private UUID id;

  @NotEmpty
  private String question;

  private List<String> answers = new ArrayList<>();

  public void setAnswers(String correctAnswer, List<String> incorrectAnswer) {
    this.answers = incorrectAnswer;
    this.answers.add(0,correctAnswer);
  }

  public void shuffle() {
    Collections.shuffle(this.answers);
  }
}
