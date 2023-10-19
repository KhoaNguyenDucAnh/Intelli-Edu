package com.intelliedu.intelliedu.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Content {

	@Id 
  @GeneratedValue 
  private Long id;

  @ElementCollection
  private List<String> keyword;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Post post;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private File file;

  private Boolean isShared;
}
