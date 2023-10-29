package com.intelliedu.intelliedu.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.File;

/**
 * FileMapper
 */
@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FileMapper {

  @Mapping(source = "id", target = "id", ignore = true)
  @Mapping(source = "createdAt", target = "createdAt", ignore = true)
  @Mapping(source = "lastOpened", target = "lastOpened", ignore = true)
  File toFile(FileDto fileDto);

  FileDto toFileDto(File file);

  List<FileDto> toFileDto(List<File> file);

  default Page<FileDto> toFileDto(Page<File> file) {
    return new PageImpl<>(toFileDto(file.getContent()), file.getPageable(), file.getTotalElements());
  }
}
