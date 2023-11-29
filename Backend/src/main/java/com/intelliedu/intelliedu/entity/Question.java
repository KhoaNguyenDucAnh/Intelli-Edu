package com.intelliedu.intelliedu.entity;

import java.util.Map;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
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
  
  @ElementCollection
  @CollectionTable
  @MapKeyColumn
  private Map<Integer, QuestionDetail> content;
}
