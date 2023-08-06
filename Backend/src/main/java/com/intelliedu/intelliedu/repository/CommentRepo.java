package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

  boolean existsByIdAndAccountId(Long id, Long accountId);

  boolean existsByIdAndPostId(Long id, Long postId);

  void deleteByIdAndAccount(Long id, Account account);
}
