package com.intelliedu.intelliedu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.MindMap;


public interface MindMapRepo extends JpaRepository<MindMap, Long> {

  List<MindMap> findByTitle(String title);

  List<MindMap> findByAccount(Account account);

  Optional<MindMap> findByTitleAndAccount(String title, Account account);

  void deleteByTitleAndAccount(String title, Account account);
}
