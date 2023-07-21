package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.MindMap;


public interface MindMapRepo extends JpaRepository<MindMap, Long> {

  Page<MindMap> findByTitle(String title, Pageable pageable);

  Page<MindMap> findByTitleAndAccountIsNot(String title, Account account, Pageable pageable);

  Page<MindMap> findByTitleAndAccount(String title, Account account, Pageable pageable);

  Optional<MindMap> findByIdAndAccount(Long id, Account account);

  void deleteByIdAndAccount(Long id, Account account);
}
