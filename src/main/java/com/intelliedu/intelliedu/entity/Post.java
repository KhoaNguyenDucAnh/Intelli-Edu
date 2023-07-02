package com.intelliedu.intelliedu.entity;

import java.time.OffsetDateTime;
import java.util.List;
import com.intelliedu.intelliedu.config.Subject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "date_time")
  private OffsetDateTime dateTime;

  private Subject subject;

  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Comment> comment;
}
