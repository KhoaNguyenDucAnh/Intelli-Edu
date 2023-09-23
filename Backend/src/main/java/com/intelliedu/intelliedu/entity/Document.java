package com.intelliedu.intelliedu.entity;

import com.intelliedu.intelliedu.config.Subject;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Document extends Content {

	private String title;

	@Enumerated(EnumType.STRING)
	private Subject subject;
  
	private String content;
}
