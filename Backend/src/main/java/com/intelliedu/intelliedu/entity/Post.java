package com.intelliedu.intelliedu.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * CommentAble
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Post {

	@Id 
  @GeneratedValue 
  private Long id;

	private Timestamp createdAt;

	private Timestamp lastOpened;

	@ManyToMany
	@JoinTable(
		name = "upvote",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "account_id")
	)
	private List<Account> upvote;

	@ManyToMany
	@JoinTable(
		name = "downvote",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "account_id")
	)
  private List<Account> downvote;
	 
  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Comment> comment;
}
