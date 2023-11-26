package com.intelliedu.intelliedu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;
import com.intelliedu.intelliedu.entity.Schedule;

/**
 * ScheduleRepo
 */
public interface ScheduleRepo extends JpaRepository<Schedule, String> {

  List<Schedule> findByAccount(Account account);

  Optional<Schedule> findByAccountAndEvent(Account account, Event event);
}
