package com.intelliedu.intelliedu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

  public List<Post> findByTitleAndContent(String title, String content);

  public Optional<Post> findByTitleAndAccount(String title, Account account);

  public List<Post> findByTitleAndContentAndAccount(String title, String content, Account account);
}
