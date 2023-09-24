package com.intelliedu.intelliedu.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.File;

/**
 * FileMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  uses = {
    DocumentMapper.class, 
    MindMapMapper.class,
    QuestionMapper.class
  }
)
public interface FileMapper {

  @BeanMapping(ignoreByDefault = true)
  @Mapping(source = "title", target = "title")
  @Mapping(source = "subject", target = "subject")
  File toFile(FileDto fileDto);

  @Mapping(source = "document", target = "documentDto")
  @Mapping(source = "mindMap", target = "mindMapDto")
  @Mapping(source = "question", target = "questionDto")
  FileDto toFileDto(File file); 
}
