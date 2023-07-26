package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.entity.Comment;

/**
 * CommentMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

  public Comment toComment(CommentDto commentDto);

  public List<CommentDto> toCommentDto(List<Comment> comment);

  default public Page<CommentDto> toCommentDto(Page<Comment> comment) {
    return new PageImpl<>(toCommentDto(comment.getContent()), comment.getPageable(), comment.getTotalElements());
  }
}
