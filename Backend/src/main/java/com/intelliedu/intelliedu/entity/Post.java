package com.intelliedu.intelliedu.entity;

import java.sql.Timestamp;
import java.util.List;

import com.intelliedu.intelliedu.config.Subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentAble
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class Post {

	@Id 
  @GeneratedValue 
  private Long id;

	private String title;

  private Subject subject;

	private Timestamp createdAt;

	private Timestamp lastOpened;

	private Integer upvote;

  private Integer downvote;
	 
  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private List<Comment> comment;
}
