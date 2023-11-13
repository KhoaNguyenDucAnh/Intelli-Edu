package com.intelliedu.intelliedu.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@SQLDelete(sql = "UPDATE document SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Document extends Content {
  
	private String content;
}
