package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Content;

/**
 * PostRepo
 */
@NoRepositoryBean
public interface ContentRepo<C extends Content> extends JpaRepository<C, String> {

	Page<C> findByKeyword(String keyword, Pageable pageable);

  Page<C> findByKeywordAndAccountIsNotAndIsSharedIsTrue(String keyword, Account account, Pageable pageable);

  Page<C> findByKeywordAndAccount(String keyword, Account account, Pageable pageable);

  Optional<C> findByIdAndAccount(String id, Account account);

  void deleteByIdAndAccount(String id, Account account);
}
