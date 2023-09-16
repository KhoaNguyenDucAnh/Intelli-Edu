package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PostMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MindMapMapper {

	@Mapping(source = "postDto.id", target = "id")
	public MindMap toMindMap(MindMapDto mindMapDto);

	@Mapping(source = "postDto.id", target = "id")
	public MindMap toMindMap(MindMapDto mindMapDto, @MappingTarget MindMap mindMap);

	@Mapping(source = ".", target = "postDto")
  public MindMapDto toMindMapDto(MindMap mindMap);

  public List<MindMapDto> toMindMapDto(List<MindMap> mindMap);

  default public Page<MindMapDto> toMindMapDto(Page<MindMap> mindMap) {
    return new PageImpl<>(toMindMapDto(mindMap.getContent()), mindMap.getPageable(), mindMap.getTotalElements());
  }
}
