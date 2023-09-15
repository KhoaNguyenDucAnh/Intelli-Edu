package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

  Optional<Comment> findByIdAndAccount(Long id, Account account);

  void deleteByIdAndAccount(Long id, Account account);
}
