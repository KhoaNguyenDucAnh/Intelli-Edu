package com.intelliedu.intelliedu.entity;

import java.util.Map;

import com.intelliedu.intelliedu.config.Subject;
import com.intelliedu.intelliedu.util.HashMapConverter;

import jakarta.persistence.Convert;
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
@DiscriminatorValue("mind_map")
public class MindMap extends Post {

	private String title;

	private Subject subject;

  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> content;
}
