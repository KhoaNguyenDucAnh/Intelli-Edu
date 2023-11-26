package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;

/**
 * FileRepo
 */
public interface FileRepo extends JpaRepository<File, UUID> {

  @Query("SELECT file FROM File file WHERE (:title = '' OR file.title = :title) AND file.account = :account")
  Page<File> findByTitleAndAccount(String title, Account account, Pageable pageable);

  Boolean existsByTitleAndAccount(String title, Account account);

  Boolean existsByIdAndAccount(UUID id, Account account);

  Optional<File> findByIdAndAccount(UUID id, Account account);

  void deleteByIdAndAccount(UUID id, Account account);
}
