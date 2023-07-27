package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {

  public void deleteByIdAndAccount(Long id, Account account);
}
