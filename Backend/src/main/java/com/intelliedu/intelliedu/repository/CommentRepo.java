package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

	Page<Comment> findByPostId(Long postId, Pageable pageable);

	Page<Comment> findByPostIdAndAccountIsNot(Long postId, Account account, Pageable pageable);

	Page<Comment> findByPostIdAndAccount(Long postId, Account account, Pageable pageable);

  Optional<Comment> findByIdAndAccount(Long id, Account account);

  void deleteByIdAndAccount(Long id, Account account);
}
