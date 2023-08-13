package com.intelliedu.intelliedu.entity;

import java.util.Map;

import com.intelliedu.intelliedu.util.HashMapConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
public class MindMap extends Post {

  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> content;
}
