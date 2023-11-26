package com.intelliedu.intelliedu.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;

/**
 * EventRepo
 */
public interface EventRepo extends JpaRepository<Event, UUID> {

	List<Event> findByScheduleAccount(Pageable pageable, Account account);

	Optional<Event> findByIdAndScheduleAccount(UUID id, Account account);

  Optional<Event> findByIdAndSharedIsTrueAndScheduleAccountIsNot(UUID id, Account account);

	void deleteByIdAndScheduleAccount(UUID id, Account account);
}
