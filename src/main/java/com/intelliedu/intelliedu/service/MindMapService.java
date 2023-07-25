package com.intelliedu.intelliedu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.mapper.MindMapMapper;
import com.intelliedu.intelliedu.repository.MindMapRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

@Service
public class MindMapService {

  @Autowired
  private MindMapMapper mindMapMapper;

  @Autowired
  private MindMapRepo mindMapRepo;

  @Autowired
  private AuthService authService;
  
  public Map<String, Page<MindMapDto>> findMindMap(String query, Authentication authentication, Pageable pageable) {
    if (authentication == null) {
      return Map.of("other", mindMapMapper.toMindMapDto(mindMapRepo.findByTitle(query, pageable))); 
    } else {
      String email = authService.getEmail(authentication);
          return Map.of("own", mindMapMapper.toMindMapDto(mindMapRepo.findByTitleAndAccountEmail(query, email, pageable)),
                      "other", mindMapMapper.toMindMapDto(mindMapRepo.findByTitleAndAccountEmailIsNot(query, email, pageable)));
    }
  }

  public MindMapDto findMindMap(Long id, Authentication authentication) {
    return mindMapMapper.toMindMapDto(mindMapRepo
        .findByIdAndAccountEmail(id, authService.getEmail(authentication))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }
  
  public void createMindMap(MindMapDto mindMapDto, Authentication authentication) {
    MindMap mindMap = mindMapMapper.toMindMap(mindMapDto);

    // Add mindmap to account
    List<MindMap> accountMindMap = authService.getAccount(authentication).getMindMap();      
    
    // Check if list null
    if (accountMindMap == null) {
      accountMindMap = new ArrayList<>();
     }

    // Check duplicate name
    if (accountMindMap.stream().anyMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMap.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    accountMindMap.add(mindMap);

    mindMapRepo.save(mindMap);
  }
  
  public void updateMindMap(MindMapDto mindMapDto, Authentication authentication) {
    mindMapRepo
      .findByIdAndAccountEmail(mindMapDto.getId(), authService.getEmail(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    // Check duplicate name
    if (authService.getAccount(authentication).getMindMap().stream().anyMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMapDto.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
   }

    mindMapRepo.save(mindMapMapper.toMindMap(mindMapDto));
  }

  public void deleteMindMap(Long id, Authentication authentication) {
    mindMapRepo.deleteByIdAndAccountEmail(id, authService.getEmail(authentication));
  }
}
