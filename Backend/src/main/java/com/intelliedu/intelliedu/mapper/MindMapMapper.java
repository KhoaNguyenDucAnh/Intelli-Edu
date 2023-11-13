package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  uses = PostMapper.class
)
public interface MindMapMapper extends ContentMapper<MindMap, MindMapDto> {

}
