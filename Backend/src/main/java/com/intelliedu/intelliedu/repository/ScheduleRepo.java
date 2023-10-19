package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Schedule;

/**
 * ScheduleRepo
 */
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

  
}
