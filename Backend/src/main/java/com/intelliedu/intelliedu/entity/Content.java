package com.intelliedu.intelliedu.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
  private UUID id;

  @Builder.Default
  @ElementCollection
  private List<String> keyword = new ArrayList<>();

  @Builder.Default
  private boolean shared = false;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  private File file;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Post post;

  //@Builder.Default
  //private boolean deleted = false;

  public void setFile(File file) {
    this.file = file;
    this.account = file.getAccount();
  }
}
