package com.intelliedu.intelliedu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.MindMapConfig;
import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.mapper.MindMapMapper;
import com.intelliedu.intelliedu.repository.MindMapRepo;
import com.intelliedu.intelliedu.security.service.AuthService;
import com.intelliedu.intelliedu.service.MindMapService;

@Service
public class MindMapServiceImpl implements MindMapService {

  @Autowired
  private MindMapMapper mindMapMapper;

  @Autowired
  private MindMapRepo mindMapRepo;

  @Autowired
  private AuthService authService;

  @Override
  public List<MindMapDto> findMindMap(String query) {
    return mindMapMapper.toMindMapDto(mindMapRepo.findByTitle(query));
  }

  @Override
  public List<MindMapDto> findMindMap(Authentication authentication) {
    return mindMapMapper.toMindMapDto(authService.getAccount(authentication).getMindMap());
  }

  @Override
  public ResponseEntity<ByteArrayResource> findMindMap(
      String title, Authentication authentication) {
    MindMap mindMap = mindMapRepo
        .findByTitleAndAccount(title, authService.getAccount(authentication))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MindMapConfig.NOT_FOUND));
    return ResponseEntity.ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(new ByteArrayResource(mindMap.getData()));
  }

  @Override
  public void createMindMap(MindMapDto mindMapDto, Authentication authentication) {
    try {
      MindMap mindMap = mindMapMapper.toMindMap(mindMapDto);

      // Add mindmap to account
      List<MindMap> accountMindMap = authService.getAccount(authentication).getMindMap();
      
      // Check if list null
      if (accountMindMap == null) {
        accountMindMap = new ArrayList<>();
      }

      // Check duplicate name
      if (accountMindMap.stream().anyMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMap.getTitle()))) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MindMapConfig.CONFLICT);
      }

      accountMindMap.add(mindMap);

      mindMapRepo.save(mindMap);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, MindMapConfig.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void updateMindMap(String title, MindMapDto mindMapDto, Authentication authentication) {
    try {
      Account account = authService.getAccount(authentication);

      MindMap mindMap = mindMapRepo
          .findByTitleAndAccount(title, account)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MindMapConfig.NOT_FOUND));

      // Check duplicate name
      if (account.getMindMap().stream().anyMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMapDto.getTitle()))) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MindMapConfig.CONFLICT);
      }

      mindMap = mindMapMapper.toMindMap(mindMapDto);

      mindMapRepo.save(mindMap);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, MindMapConfig.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void deleteMindMap(String title, Authentication authentication) {
    mindMapRepo.deleteByTitleAndAccount(title, authService.getAccount(authentication));
  }
}
