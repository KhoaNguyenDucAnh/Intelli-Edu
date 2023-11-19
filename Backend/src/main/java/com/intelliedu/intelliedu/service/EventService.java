package com.intelliedu.intelliedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;
import com.intelliedu.intelliedu.entity.Schedule;
import com.intelliedu.intelliedu.mapper.EventMapper;
import com.intelliedu.intelliedu.repository.EventRepo;
import com.intelliedu.intelliedu.repository.ScheduleRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * EventService
 */
@Service
public class EventService {

	@Autowired
	private EventRepo eventRepo;

  @Autowired
  private ScheduleRepo scheduleRepo;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private AuthService authService;

	public List<EventDto> findEvent(Authentication authentication) {
		return eventMapper.toEventDto(scheduleRepo.findByAccount(authService.getAccount(authentication)).stream().map(s -> s.getEvent()).toList());
	}

	public EventDto createEvent(EventDto eventDto, Authentication authentication) {
		System.out.println(eventDto);
    Event event = eventMapper.toEvent(eventDto);
    System.out.println(event);
    event.addSchedule(Schedule.builder().account(authService.getAccount(authentication)).event(event).owned(true).build());
    return eventMapper.toEventDto(eventRepo.save(event));
	}

	public EventDto updateEvent(EventDto eventDto, Authentication authentication) {
    Event event = eventRepo
			.findByIdAndScheduleAccount(eventDto.getId(), authService.getAccount(authentication))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return eventMapper.toEventDto(eventRepo.save(eventMapper.toEvent(eventDto, event)));
  }

	@Transactional
	public void deleteEvent(Long id, Authentication authentication) {
    Schedule schedule = scheduleRepo
      .findByAccountAndEvent(
        authService.getAccount(authentication),
        eventRepo
          .findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    if (schedule.isOwned()) {
		  eventRepo.deleteByIdAndScheduleAccount(id, authService.getAccount(authentication));
	  } else {
      scheduleRepo.delete(schedule);
    }
  }

  public EventDto addSharedEvent(Long id, Authentication authentication) {
    Account account = authService.getAccount(authentication);
    Event event = eventRepo.findByIdAndSharedIsTrueAndScheduleAccountIsNot(id, account).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    event.addSchedule(Schedule.builder().account(account).event(event).build());
    return eventMapper.toEventDto(eventRepo.save(event));
  }
}
