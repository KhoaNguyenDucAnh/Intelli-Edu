package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
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

	@Mapping(source = "id", target = "id", ignore = true)
	@Mapping(source = "createdAt", target = "createdAt", ignore = true)
	@Mapping(source = "lastOpened", target = "lastOpened", ignore = true)
	@Mapping(source = "upvote", target = ".", ignore = true)
	@Mapping(source = "downvote", target = ".", ignore = true)
	public Post toPost(PostDto postDto);

	public PostDto toPostDto(Post post);
}
