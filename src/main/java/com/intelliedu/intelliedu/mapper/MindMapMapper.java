package com.intelliedu.intelliedu.mapper;

import java.io.IOException;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MindMapMapper {

  public MindMap toMindMap(MindMapDto mindMapDto) throws IOException;

  public List<MindMapDto> toMindMapDto(List<MindMap> mindMap);

  default public Page<MindMapDto> toMindMapDto(Page<MindMap> mindMap) {
    return new PageImpl<>(toMindMapDto(mindMap.getContent()), mindMap.getPageable(), mindMap.getTotalElements());
  }

  default public byte[] map(MultipartFile value) throws IOException {
    return value.getBytes();
  }

  default public MultipartFile map(byte[] value) {
    return null;
  }
}
