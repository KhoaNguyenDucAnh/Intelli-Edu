package com.intelliedu.intelliedu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;

/**
 * EventRepo
 */
public interface EventRepo extends JpaRepository<Event, String> {

	List<Event> findByScheduleAccount(Pageable pageable, Account account);

	Optional<Event> findByIdAndScheduleAccount(String id, Account account);

  Optional<Event> findByIdAndSharedIsTrueAndScheduleAccountIsNot(String id, Account account);

	void deleteByIdAndScheduleAccount(String id, Account account);
}
