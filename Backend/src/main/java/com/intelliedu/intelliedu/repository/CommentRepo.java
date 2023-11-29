package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Comment;

@NoRepositoryBean
public interface CommentRepo extends JpaRepository<Comment, UUID> {

	Page<Comment> findByPostId(UUID postId, Pageable pageable);

	Page<Comment> findByPostIdAndAccountIsNot(UUID postId, Account account, Pageable pageable);

	Page<Comment> findByPostIdAndAccount(UUID postId, Account account, Pageable pageable);

  Optional<Comment> findByIdAndAccount(UUID id, Account account);

  void deleteByIdAndAccount(UUID id, Account account);
}
