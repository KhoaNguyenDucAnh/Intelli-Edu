package com.intelliedu.intelliedu.entity;

import java.util.Map;

import com.intelliedu.intelliedu.util.HashMapConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
//@SQLDelete(sql = "UPDATE mind_map SET deleted = true WHERE file_id=?")
//@Where(clause = "deleted=false")
public class MindMap extends Content {

  @Column(columnDefinition = "TEXT")
  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> content;

  @Column(columnDefinition = "TEXT")
  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> preContent;

  @Column(columnDefinition = "TEXT")
  private String feedback;
}
