package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

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

  @Mapping(target = "shared", ignore = true)
	Event toEvent(EventDto eventDto);

  @InheritConfiguration
	Event toEvent(EventDto eventDto, @MappingTarget Event event);

  EventDto toEventDto(Event event);

  List<EventDto> toEventDto(List<Event> event);

  /*default Page<EventDto> toEventDto(Page<Event> event) {
    return new PageImpl<>(toEventDto(event.getContent()), event.getPageable(), event.getTotalElements());
  }*/
}
