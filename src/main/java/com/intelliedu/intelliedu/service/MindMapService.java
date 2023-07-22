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

import com.intelliedu.intelliedu.config.MindMapConfig;
import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.Account;
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
      Account account = authService.getAccount(authentication);
      return Map.of("own", mindMapMapper.toMindMapDto(mindMapRepo.findByTitleAndAccount(query, account, pageable)),
                  "other", mindMapMapper.toMindMapDto(mindMapRepo.findByTitleAndAccountIsNot(query, account, pageable)));
    }
  }

  public MindMapDto findMindMap(Long id, Authentication authentication) {
    return mindMapMapper.toMindMapDto(mindMapRepo
        .findByIdAndAccount(id, authService.getAccount(authentication))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MindMapConfig.NOT_FOUND)));
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
      throw new ResponseStatusException(HttpStatus.CONFLICT, MindMapConfig.CONFLICT);
    }

    accountMindMap.add(mindMap);

    mindMapRepo.save(mindMap);
  }
  
  public void updateMindMap(Long id, MindMapDto mindMapDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    MindMap mindMap = mindMapRepo
      .findByIdAndAccount(id, account)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MindMapConfig.NOT_FOUND));

    // Check duplicate name
    if (account.getMindMap().stream().anyMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMapDto.getTitle()))) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, MindMapConfig.CONFLICT);
    }

    mindMap = mindMapMapper.toMindMap(mindMapDto);

    mindMapRepo.save(mindMap);
  }

  public void deleteMindMap(Long id, Authentication authentication) {
    mindMapRepo.deleteByIdAndAccount(id, authService.getAccount(authentication));
  }
}
