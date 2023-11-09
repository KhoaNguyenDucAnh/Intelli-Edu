package com.intelliedu.intelliedu.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

  @Builder.Default
  @ElementCollection
  private List<String> keyword = new ArrayList<>();

  private Boolean isShared;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private File file;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Post post;

  public void setFile(File file) {
    this.file = file;
    this.account = file.getAccount();
  }
}
