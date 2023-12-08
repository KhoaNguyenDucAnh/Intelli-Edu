package com.intelliedu.intelliedu.mapper;

import java.time.format.DateTimeFormatter;
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
public abstract class EventMapper {

  private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

  private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

  @Mapping(target = "shared", ignore = true)
	public abstract Event toEvent(EventDto eventDto);

  @InheritConfiguration
	public abstract Event toEvent(EventDto eventDto, @MappingTarget Event event);

  public abstract EventDto toEventDto(Event event);

  public List<Map<String, String>> toEventDtoHelper(List<Event> event) {
    return event.stream().map(e ->
      Map.of(
        "name", e.getName(),
        "time", timeFormatter.format(e.getTime()),
        "description", e.getDescription()
      )
    ).toList();
  }

  public Map<String, List<Object>> toEventDto(List<Event> event) {
    Map<String, List<Object>> eventDto = new HashMap<>();
    event.stream().forEach(e ->
      addValue(
        eventDto, 
        dateFormatter.format(e.getDate()), 
        Map.of(
          "id", e.getId().toString(),
          "name", e.getName(),
          "time", timeFormatter.format(e.getTime()),
          "description", e.getDescription(),
          "shared", e.isShared()
        )
      )
    );
    return eventDto;
  }

  private static void addValue(Map<String, List<Object>> map, String key, Object value) {
    map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
  }
}
