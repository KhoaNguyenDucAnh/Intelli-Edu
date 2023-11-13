package com.intelliedu.intelliedu.entity;

import java.util.Map;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.intelliedu.intelliedu.util.HashMapConverter;

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
@SQLDelete(sql = "UPDATE mind_map SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class MindMap extends Content {

  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> content;
}
