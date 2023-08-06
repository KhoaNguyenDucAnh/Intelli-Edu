package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.PostDto;
import com.intelliedu.intelliedu.entity.Post;

/**
 * PostMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  public Post toPost(PostDto postDto);

  public PostDto toPostDto(Post post);

  public List<PostDto> toPostDto(List<Post> Post);

  default public Page<PostDto> toPostDto(Page<Post> post) {
    return new PageImpl<>(toPostDto(post.getContent()), post.getPageable(), post.getTotalElements());
  }
}
