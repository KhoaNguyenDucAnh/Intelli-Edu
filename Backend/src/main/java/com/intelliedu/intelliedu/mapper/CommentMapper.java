package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.entity.Comment;

/**
 * CommentMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PostMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

	@Mapping(source = "postDto.id", target = "id")
  public Comment toComment(CommentDto commentDto);

	@Mapping(source = "postDto.id", target = "id")
	public Comment toComment(CommentDto commentDto, @MappingTarget Comment comment);

	@Mapping(source = ".", target = "postDto")
  public CommentDto toCommentDto(Comment comment);

  public List<CommentDto> toCommentDto(List<Comment> comment);

  default public Page<CommentDto> toCommentDto(Page<Comment> comment) {
    return new PageImpl<>(toCommentDto(comment.getContent()), comment.getPageable(), comment.getTotalElements());
  }
}
