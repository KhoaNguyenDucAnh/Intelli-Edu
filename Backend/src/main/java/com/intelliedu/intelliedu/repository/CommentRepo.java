package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, String> {

	Page<Comment> findByPostId(String postId, Pageable pageable);

	Page<Comment> findByPostIdAndAccountIsNot(String postId, Account account, Pageable pageable);

	Page<Comment> findByPostIdAndAccount(String postId, Account account, Pageable pageable);

  Optional<Comment> findByIdAndAccount(String id, Account account);

  void deleteByIdAndAccount(String id, Account account);
}
