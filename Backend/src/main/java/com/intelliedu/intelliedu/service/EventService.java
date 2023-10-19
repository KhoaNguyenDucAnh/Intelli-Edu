package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.entity.Event;
import com.intelliedu.intelliedu.mapper.EventMapper;
import com.intelliedu.intelliedu.repository.EventRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * EventService
 */
@Service
public class EventService {

	@Autowired
	private EventRepo eventRepo;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private AuthService authService;

	public Page<EventDto> findEvent(Pageable pageable, Authentication authentication) {
		return eventMapper.toEventDto(eventRepo.findByScheduleAccount(pageable, authService.getAccount(authentication)));
	}

	public EventDto createEvent(EventDto eventDto, Authentication authentication) {
		Event event = eventMapper.toEvent(eventDto);
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
		eventRepo.deleteByIdAndScheduleAccount(id, authService.getAccount(authentication));
	}
}
