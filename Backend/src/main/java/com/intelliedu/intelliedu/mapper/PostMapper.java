package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Post;

/**
 * PostMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
@MapperConfig
public interface PostMapper {

	PostDto toPostDto(Post post);
}
