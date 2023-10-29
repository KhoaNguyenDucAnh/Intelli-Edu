package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;

@Service
public class MindMapService extends ContentService<MindMap, MindMapDto> {

  @Override
  protected MindMap createContent() {
    return new MindMap(); 
  }
} 
