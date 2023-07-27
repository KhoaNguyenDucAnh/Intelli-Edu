package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intelliedu.intelliedu.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

  Page<Post> findByTitleOrContent(String title, String content, Pageable pageable);

  @Query(
    value = 
    """
    select
      p.id,
      p.account_id,
      p.content,
      p.date_time,
      p.is_answered,
      p.subject,
      p.title 
    from
      post p
    where
      (p.title=? 
      or p.content=?) 
      and p.account_id!=?
    """,
    nativeQuery = true
  )
  Page<Post> findByTitleOrContentAndAccountIdIsNot(String title, String content, Long acountId, Pageable pageable);
  
  @Query(
    value = 
    """
    select
      p.id,
      p.account_id,
      p.content,
      p.date_time,
      p.is_answered,
      p.subject,
      p.title 
    from
      post p
    where
      (p.title=? 
      or p.content=?) 
      and p.account_id=?
    """,
    nativeQuery = true
  )
  Page<Post> findByTitleOrContentAndAccountId(String title, String content, Long accountId, Pageable pageable);

  Optional<Post> findByIdAndAccountEmail(Long id, String email);

  @Query(
    value = 
    """
    select
      count(1)
    from
      comment c
    where
      c.id=?
      and c.post_id=?
    """,
    nativeQuery = true
  )
  Long existCommentWithId(Long commentId, Long postId);

  void deleteByIdAndAccountEmail(Long id, String email);
}
