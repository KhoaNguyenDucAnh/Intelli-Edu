package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.mapper.MindMapMapper;

@Service
public class MindMapService extends ContentService<MindMap, MindMapDto, MindMapMapper> {

  @Override
  protected MindMap createContent(File file) {
    if (file.getMindMap() != null) {
      return file.getMindMap();
    }

    MindMap mindMap = new MindMap();

    file.setMindMap(mindMap);
    mindMap.setFile(file);

    return mindMap;
  }
} 
