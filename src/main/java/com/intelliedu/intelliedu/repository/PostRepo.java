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
        p1_0.id,
        p1_0.account_email,
        p1_0.content,
        p1_0.date_time,
        p1_0.is_answered,
        p1_0.subject,
        p1_0.title 
    from
        post p1_0 
    where
        (p1_0.title=? 
        or p1_0.content=?) 
        and p1_0.account_email!=?
    """,
    nativeQuery = true
  )
  Page<Post> findByTitleOrContentAndAccountEmailIsNot(String title, String content, String email, Pageable pageable);

  @Query(
    value = 
    """
    select
        p1_0.id,
        p1_0.account_email,
        p1_0.content,
        p1_0.date_time,
        p1_0.is_answered,
        p1_0.subject,
        p1_0.title 
    from
        post p1_0 
    where
        (p1_0.title=? 
        or p1_0.content=?) 
        and p1_0.account_email=?
    """,
    nativeQuery = true
  )
  Page<Post> findByTitleOrContentAndAccountEmail(String title, String content, String email, Pageable pageable);

  Optional<Post> findByIdAndAccountEmail(Long id, String email);

  @Query(value = "SELECT COUNT(1) FROM Comment c WHERE c.id = :comment_id AND c.post_id = :post_id", nativeQuery = true)
  Long existCommentWithId(@Param("comment_id") Long commentId, @Param("post_id") Long postId);

  void deleteByIdAndAccountEmail(Long id, String email);
}
