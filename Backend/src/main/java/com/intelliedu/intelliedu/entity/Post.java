package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.intelliedu.intelliedu.config.VoteStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentAble
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID) 
  private UUID id;

  @CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime lastOpened;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;

  @Builder.Default
  private Integer view = 0;

  @Builder.Default
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Vote> vote = new ArrayList<>();

  private Integer upvote;

  private Integer downvote;

  @PrePersist
  @PreUpdate
  public void prePersistOrUpdate() {
    this.upvote = this.vote.stream().filter(v -> v.getVoteStatus() == VoteStatus.UPVOTE).toList().size();
    this.downvote = this.vote.stream().filter(v -> v.getVoteStatus() == VoteStatus.DOWNVOTE).toList().size();
  }

  public Integer view() {
    return ++this.view;
  }

  //@Builder.Default
	//@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  //private List<Comment> comment = new ArrayList<>();
}
