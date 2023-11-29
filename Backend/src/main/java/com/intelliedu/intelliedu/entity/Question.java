package com.intelliedu.intelliedu.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE file_id=?")
@Where(clause = "deleted=false")
public class Question extends Content {

  @Builder.Default
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<QuestionDetail> content = new ArrayList<>();
}
