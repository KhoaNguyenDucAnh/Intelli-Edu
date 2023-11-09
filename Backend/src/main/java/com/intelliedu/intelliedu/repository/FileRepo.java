package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;

/**
 * FileRepo
 */
public interface FileRepo extends JpaRepository<File, String> {

  Page<File> findByTitleAndAccount(String title, Account account, Pageable pageable);

  Boolean existsByTitleAndAccount(String title, Account account);

  Optional<File> findByIdAndAccount(String id, Account account);

  void deleteByIdAndAccount(String id, Account account);
}
