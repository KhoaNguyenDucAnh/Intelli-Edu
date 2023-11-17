package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Content;

/**
 * PostRepo
 */
@NoRepositoryBean
public interface ContentRepo<C extends Content> extends JpaRepository<C, String> {

  @Query("SELECT content FROM #{#entityName} content WHERE :keyword = '' OR :keyword MEMBER OF content.keyword")
	Page<C> findByKeyword(String keyword, Pageable pageable);

  @Query("SELECT content FROM #{#entityName} content WHERE (:keyword = '' OR :keyword MEMBER OF content.keyword) AND content.account != :account AND content.shared")
  Page<C> findByKeywordAndAccountIsNotAndSharedIsTrue(String keyword, Account account, Pageable pageable);

  @Query("SELECT content FROM #{#entityName} content WHERE (:keyword = '' OR :keyword MEMBER OF content.keyword) AND content.account = :account")
  Page<C> findByKeywordAndAccount(String keyword, Account account, Pageable pageable);

  Boolean existsByIdAndAccount(String id, Account account);

  Optional<C> findByIdAndAccount(String id, Account account);
}
