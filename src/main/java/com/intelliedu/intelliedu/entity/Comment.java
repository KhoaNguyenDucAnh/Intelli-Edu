package com.intelliedu.intelliedu.entity;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "date_time")
  private OffsetDateTime dateTime;

  private String content;

  private Integer upvote;

  private Integer downvote;

  @Column(name = "is_answer")
  private Boolean isAnswer;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @ManyToMany
  @JoinTable(joinColumns = @JoinColumn(name = "parent_comment"), inverseJoinColumns = @JoinColumn(name = "child_comment"))
  private List<Comment> comment;
}
