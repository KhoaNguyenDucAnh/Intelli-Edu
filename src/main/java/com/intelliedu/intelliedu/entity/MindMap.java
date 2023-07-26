package com.intelliedu.intelliedu.entity;

import java.util.Map;

import com.intelliedu.intelliedu.util.HashMapConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mindmap")
public class MindMap {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  @Convert(converter = HashMapConverter.class)
  private Map<String, Object> data;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;
}
