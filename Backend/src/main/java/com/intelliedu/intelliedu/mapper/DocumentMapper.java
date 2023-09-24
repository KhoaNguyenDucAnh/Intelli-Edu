package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.entity.Document;

/**
 * DocumentMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DocumentMapper extends ContentMapper<Document, DocumentDto> {
  
}
