package com.intelliedu.intelliedu.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Post;

/**
 * PostMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

	@Mapping(source = "upvote", target = "upvote", ignore = true)
	@Mapping(source = "downvote", target = "downvote", ignore = true)
	public Post toPost(PostDto postDto);

	@InheritInverseConfiguration
	public PostDto toPostDto(Post post);
}
