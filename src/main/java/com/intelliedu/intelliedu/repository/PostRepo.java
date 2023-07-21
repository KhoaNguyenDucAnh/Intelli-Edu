package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

  Page<Post> findByTitleAndContent(String title, String content, Pageable pageable);

  Page<Post> findByTitleAndContentAndAccountIsNot(String title, String content, Account account, Pageable pageable);

  Page<Post> findByTitleAndContentAndAccount(String title, String content, Account account, Pageable pageable);

  Optional<Post> findByIdAndAccount(Long id, Account account);

  void deleteByIdAndAccount(Long id, Account account);
}
