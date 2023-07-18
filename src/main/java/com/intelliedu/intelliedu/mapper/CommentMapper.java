package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.CommentDto;
import com.intelliedu.intelliedu.entity.Comment;

/**
 * CommentMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

  public Comment toComment(CommentDto commentDto);
}
