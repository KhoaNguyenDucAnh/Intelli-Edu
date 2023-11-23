package com.intelliedu.intelliedu.entity;

import java.util.Map;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Question extends Content {
  
  @Column(columnDefinition = "TEXT")
  private Map<String, String> content;
}
