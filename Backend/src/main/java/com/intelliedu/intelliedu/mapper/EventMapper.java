package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.entity.Event;

/**
 * EventMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EventMapper {
  
	public Event toEvent(EventDto eventDto);

	public Event toEvent(EventDto eventDto, @MappingTarget Event event);

  public EventDto toEventDto(Event event);

  public List<EventDto> toEventDto(List<Event> event);

  default public Page<EventDto> toEventDto(Page<Event> event) {
    return new PageImpl<>(toEventDto(event.getContent()), event.getPageable(), event.getTotalElements());
  }

}
