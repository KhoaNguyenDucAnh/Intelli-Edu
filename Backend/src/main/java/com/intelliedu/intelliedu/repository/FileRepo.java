package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;

/**
 * FileRepo
 */
public interface FileRepo extends JpaRepository<File, Long> {

  Boolean existsByTitleAndAccount(String title, Account account);

  Optional<File> findByTokenAndAccount(String token, Account Account);

  Optional<File> findByToken(String token);
}
