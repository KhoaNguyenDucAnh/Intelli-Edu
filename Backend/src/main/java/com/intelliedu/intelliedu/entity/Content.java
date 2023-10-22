package com.intelliedu.intelliedu.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.MapsId;
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
@MappedSuperclass
public class Content {

	@Id
  private String id;

  @ElementCollection
  private List<String> keyword;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private File file;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Post post;
  
  private Boolean isShared;

  public void addKeyword(String keyword) {
    if (this.keyword == null) {
      this.keyword = new ArrayList<>();
    }

    this.keyword.add(keyword);
  }
}
