package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
  @GenericGenerator(name = "UUID", strategy = "com.intelliedu.intelliedu.util.IdGenerator")
  @GeneratedValue(generator = "UUID", strategy = GenerationType.SEQUENCE) 
  private String id;

  @CreationTimestamp
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	private ZonedDateTime lastOpened;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;

  @Builder.Default
	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Vote> vote = new ArrayList<>();

  //@Builder.Default
	//@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  //private List<Comment> comment = new ArrayList<>();
}
