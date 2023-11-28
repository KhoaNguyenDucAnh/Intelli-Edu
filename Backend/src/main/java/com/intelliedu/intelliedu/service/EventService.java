package com.intelliedu.intelliedu.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;
import com.intelliedu.intelliedu.entity.Schedule;
import com.intelliedu.intelliedu.exception.AlreadyExistsException;
import com.intelliedu.intelliedu.exception.NotFoundException;
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

  @Value("${domain}")
  private String domain;

	public Map<String, List<Object>> findEvent(Authentication authentication) {
		return eventMapper.toEventDto(scheduleRepo.findByAccount(authService.getAccount(authentication)).stream().map(s -> s.getEvent()).toList());
	}

	public EventDto createEvent(EventDto eventDto, Authentication authentication) {
    Event event = eventMapper.toEvent(eventDto);
    event.addSchedule(Schedule.builder().account(authService.getAccount(authentication)).event(event).owned(true).build());
    return eventMapper.toEventDto(eventRepo.save(event));
	}

	public EventDto updateEvent(UUID id, EventDto eventDto, Authentication authentication) {
    Event event = eventRepo
			.findByIdAndScheduleAccount(id, authService.getAccount(authentication))
			.orElseThrow(() -> new NotFoundException(Event.class, id));

    return eventMapper.toEventDto(eventRepo.save(eventMapper.toEvent(eventDto, event)));
  }

	@Transactional
	public void deleteEvent(UUID id, Authentication authentication) {
    Schedule schedule = scheduleRepo
      .findByAccountAndEvent(
        authService.getAccount(authentication),
        eventRepo
          .findById(id)
          .orElseThrow(() -> new NotFoundException(Event.class, id)))
      .orElseThrow(() -> new NotFoundException(Schedule.class, id));
    
    if (schedule.isOwned()) {
		  eventRepo.deleteByIdAndScheduleAccount(id, authService.getAccount(authentication));
	  } else {
      scheduleRepo.delete(schedule);
    }
  }

  public String shareEvent(UUID id, Authentication authentication) {
    Event event = eventRepo
			.findByIdAndScheduleAccount(id, authService.getAccount(authentication))
			.orElseThrow(() -> new NotFoundException(Event.class, id));
    event.setShared(true);
    eventRepo.save(event);
    return "http://" + domain + "/share/event/" + id.toString();
  }

  public EventDto addSharedEvent(UUID id, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (eventRepo.existsByIdAndScheduleAccount(id, account)) {
      throw new AlreadyExistsException(Event.class, id);
    }

    Event event = eventRepo.findByIdAndSharedIsTrue(id).orElseThrow(() -> new NotFoundException(Event.class, id));
    event.addSchedule(Schedule.builder().account(account).event(event).build());
    
    return eventMapper.toEventDto(eventRepo.save(event));
  }
}
