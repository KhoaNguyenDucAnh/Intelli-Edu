package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;

/**
 * FileRepo
 */
public interface FileRepo extends JpaRepository<File, String> {

  @Query("SELECT file FROM File file WHERE (:title = '' OR file.title = :title) AND file.account = :account")
  Page<File> findByTitleAndAccount(String title, Account account, Pageable pageable);

  Boolean existsByTitleAndAccount(String title, Account account);

  Optional<File> findByIdAndAccount(String id, Account Account);

  Optional<File> findById(String id);
}
