package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.ContentDto;
import com.intelliedu.intelliedu.entity.Content;

/**
 * ContentMapper
 */
public interface ContentMapper<C extends Content, CDto extends ContentDto> {
 
  @Mapping(target = "shared", ignore = true)
  @Mapping(target = "post", ignore = true)
  C toEntity(CDto cDto);

  @InheritConfiguration
	C toEntity(CDto cDto, @MappingTarget C c);

  CDto toDto(C c);

  List<CDto> toDto(List<C> c);

  default Page<CDto> toDto(Page<C> c) {
    return new PageImpl<>(toDto(c.getContent()), c.getPageable(), c.getTotalElements());
  }
}
