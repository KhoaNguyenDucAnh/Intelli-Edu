package com.intelliedu.intelliedu.service;

import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.intelliedu.intelliedu.dto.MindMapDto;

public interface MindMapService {

  public List<MindMapDto> findMindMapByUser(Authentication authentication);

  public ResponseEntity<ByteArrayResource> findMindMapById(String rawId);

  public List<MindMapDto> findMindMapByTitle(String title);

  public void addMindMap(MindMapDto mindMapDto);

  public void updateMindMap(String rawId, MindMapDto mindMapDto);

  public void deleteMindMap(String rawId);
}
