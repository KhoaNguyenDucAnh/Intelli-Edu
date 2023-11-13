package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.QuestionDto;
import com.intelliedu.intelliedu.entity.Question;

/**
 * QuestionMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  uses = PostMapper.class
)
public interface QuestionMapper extends ContentMapper<Question, QuestionDto> {

}
