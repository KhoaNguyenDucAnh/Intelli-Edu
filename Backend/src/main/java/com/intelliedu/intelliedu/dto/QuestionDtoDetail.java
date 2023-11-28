package com.intelliedu.intelliedu.dto;

import java.util.ArrayList;
import java.util.List;

import com.intelliedu.intelliedu.entity.QuestionDetail;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QuestionDtoDetail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDtoDetail {

  @NotEmpty
  private String question;

  @Builder.Default
  private List<String> answer = new ArrayList<>();

  //public void setAnswer(QuestionDetail questionDetail) {
    /*answer.add(questionDetail.getCorrectAnswer());
    answer.add(questionDetail.getIncorrectAnswer1());
    answer.add(questionDetail.getIncorrectAnswer2());
    answer.add(questionDetail.getIncorrectAnswer3());*/
  //}
}
