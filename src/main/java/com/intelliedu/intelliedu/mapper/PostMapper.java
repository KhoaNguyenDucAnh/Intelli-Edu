package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Post;

/**
 * PostMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  public Post toPost(PostDto postDto);

  public List<PostDto> toPostDto(List<Post> Post);
}
