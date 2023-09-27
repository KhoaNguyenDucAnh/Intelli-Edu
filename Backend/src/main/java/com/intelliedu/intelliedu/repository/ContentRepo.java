package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Content;

/**
 * PostRepo
 */
public interface ContentRepo<C extends Content> extends JpaRepository<C, Long> {

	Page<C> findByKeyword(String keyword, Pageable pageable);

  Page<C> findByKeywordAndFileAccountIsNot(String keyword, Account account, Pageable pageable);

  Page<C> findByKeywordAndFileAccount(String keyword, Account account, Pageable pageable);

  Optional<C> findByIdAndFileAccount(Long id, Account Account);

  void deleteByIdAndFileAccount(Long id, Account account);

}
