package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;

@Service
public class MindMapService extends ContentService<MindMap, MindMapDto> {

  @Override
  protected MindMap createContent(String title) {
    return MindMap.builder().content(Map.of("title", title)).build();
  }

  @Override
  protected Class<MindMap> getGenericClass() {
    return MindMap.class; 
  }
} 
