package com.intelliedu.intelliedu.entity;

import com.intelliedu.intelliedu.config.Subject;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("document")
public class Document extends Post {

	private String title;

	private Subject subject;
  
	private String content;
}
