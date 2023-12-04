package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.QuestionDetail;

/**
 * QuestionDetailRepo
 */
public interface QuestionDetailRepo extends JpaRepository<QuestionDetail, UUID> {

  Optional<QuestionDetail> findByIdAndParentIdAndParentAccount(UUID id, UUID questionId, Account account);
}
