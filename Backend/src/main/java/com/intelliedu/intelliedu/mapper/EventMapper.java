package com.intelliedu.intelliedu.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  default Map<String, List<Object>> toEventDto(List<Event> event) {
    Map<String, List<Object>> eventDto = new HashMap<>();
    event.stream().forEach(e -> {
      addValue(
        eventDto, 
        e.getDeadline().toLocalDate().toString(), 
        Map.of(
          "id", e.getId().toString(), 
          "name", e.getName(), 
          "time", e.getDeadline().toLocalTime().toString(),
          "description", e.getDescription()
        )
      );
      addValue(eventDto, "deadline", e.getDeadline().toLocalDate().toString());
    });
    return eventDto;
  }

  private static void addValue(Map<String, List<Object>> map, String key, Object value) {
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
  }

  /*default Page<EventDto> toEventDto(Page<Event> event) {
    return new PageImpl<>(toEventDto(event.getContent()), event.getPageable(), event.getTotalElements());
  }*/
}
