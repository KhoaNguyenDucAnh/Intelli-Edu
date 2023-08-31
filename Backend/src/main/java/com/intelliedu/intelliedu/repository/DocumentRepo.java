package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Document;

public interface DocumentRepo extends JpaRepository<Document, Long> {

	Page<Document> findByTitle(String title, Pageable pageable);

  Page<Document> findByTitleAndAccountIsNot(String title, Account account, Pageable pageable);

  Page<Document> findByTitleAndAccount(String title, Account account, Pageable pageable);

	Optional<Document> findByTitleAndAccount(String title, Account account);

	Boolean existsByIdIsNotAndTitleAndAccount(Long id, String title, Account account);

  Optional<Document> findByIdAndAccount(Long id, Account Account);

  void deleteByIdAndAccount(Long id, Account account);

}
