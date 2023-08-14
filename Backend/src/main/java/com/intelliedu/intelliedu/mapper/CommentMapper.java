package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.entity.Comment;

/**
 * CommentMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PostMapper.class)
public interface CommentMapper {

	@Mapping(source = "postDto", target = ".")
	@Mapping(source = "postDto.upvote", target = "upvote", ignore = true)
	@Mapping(source = "postDto.downvote", target = "downvote", ignore = true)
  public Comment toComment(CommentDto commentDto);

	@Mapping(source = ".", target = "postDto")
  public CommentDto toCommentDto(Comment comment);

  public List<CommentDto> toCommentDto(List<Comment> comment);

  default public Page<CommentDto> toCommentDto(Page<Comment> comment) {
    return new PageImpl<>(toCommentDto(comment.getContent()), comment.getPageable(), comment.getTotalElements());
  }
}
