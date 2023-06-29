package com.intelliedu.intelliedu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.MindMap;


public interface MindMapRepo extends JpaRepository<MindMap, Long> {

  List<MindMap> findByTitle(String title);

  List<MindMap> findByAccount(Account account);
}
