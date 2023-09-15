package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.entity.Document;

/**
 * DocumentMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = PostMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DocumentMapper {

	@Mapping(source = "postDto.id", target = "id")
  public Document toDocument(DocumentDto documentDto);

	@Mapping(source = "postDto.id", target = "id")
	public Document toDocument(DocumentDto documentDto, @MappingTarget Document document);

	@Mapping(source = ".", target = "postDto")
  public DocumentDto toDocumentDto(Document document);

  public List<DocumentDto> toDocumentDto(List<Document> document);

  default public Page<DocumentDto> toDocumentDto(Page<Document> document) {
    return new PageImpl<>(toDocumentDto(document.getContent()), document.getPageable(), document.getTotalElements());
  }

}
