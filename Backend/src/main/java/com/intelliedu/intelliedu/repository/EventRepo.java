package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;

/**
 * EventRepo
 */
public interface EventRepo extends JpaRepository<Event, Long> {

	Page<Event> findByAccount(Account account);

	Optional<Event> findByIdAndAccount(Long id, Account account);

	void deleteByIdAndAccoutn(Long id, Account account);
}
