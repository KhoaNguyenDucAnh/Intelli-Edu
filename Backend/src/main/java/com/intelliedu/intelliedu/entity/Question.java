package com.intelliedu.intelliedu.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Question
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
public class Question extends Content {

  @Builder.Default
  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<QuestionDetail> content = new ArrayList<>();

  public void addContent(QuestionDetail questionDetail) {
    this.content.add(questionDetail);
    questionDetail.setParent(this);
  }

  public void addContent(List<QuestionDetail> questionDetail) {
    questionDetail.forEach(question -> addContent(question));
  }
}
