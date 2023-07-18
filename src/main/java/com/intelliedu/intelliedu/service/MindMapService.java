package com.intelliedu.intelliedu.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.intelliedu.intelliedu.dto.MindMapDto;

public interface MindMapService {

  public List<MindMapDto> findMindMap(String query);

  public List<MindMapDto> findMindMap(Authentication authentication);

  public ResponseEntity<ByteArrayResource> findMindMap(String title, Authentication authentication);

  public void createMindMap(MindMapDto mindMapDto, Authentication authentication);

  public void updateMindMap(String title, MindMapDto mindMapDto, Authentication authentication);

  public void deleteMindMap(String title, Authentication authentication);
}
