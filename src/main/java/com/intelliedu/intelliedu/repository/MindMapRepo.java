package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.MindMap;


public interface MindMapRepo extends JpaRepository<MindMap, Long> {

  Page<MindMap> findByTitle(String title, Pageable pageable);

  Page<MindMap> findByTitleAndAccountEmailIsNot(String title, String email, Pageable pageable);

  Page<MindMap> findByTitleAndAccountEmail(String title, String email, Pageable pageable);

  Optional<MindMap> findByIdAndAccountEmail(Long id, String email);

  void deleteByIdAndAccountEmail(Long id, String email);
}
