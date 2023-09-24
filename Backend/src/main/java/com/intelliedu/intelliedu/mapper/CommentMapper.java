package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;
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
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
	mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
	config = PostMapper.class
)
public interface CommentMapper {

	Comment toComment(CommentDto commentDto);	

	Comment toComment(CommentDto commentDto, @MappingTarget Comment comment);

  CommentDto toCommentDto(Comment comment);

  List<CommentDto> toCommentDto(List<Comment> comment);

  default Page<CommentDto> toCommentDto(Page<Comment> comment) {
    return new PageImpl<>(toCommentDto(comment.getContent()), comment.getPageable(), comment.getTotalElements());
  }
}
