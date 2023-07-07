package com.intelliedu.intelliedu.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.config.MindMapConfig;
import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.mapper.MindMapMapper;
import com.intelliedu.intelliedu.repository.AccountRepo;
import com.intelliedu.intelliedu.repository.MindMapRepo;
import com.intelliedu.intelliedu.service.MindMapService;

@Service
public class MindMapServiceImpl implements MindMapService {

  @Autowired
  private MindMapMapper mindMapMapper;

  @Autowired
  private MindMapRepo mindMapRepo;

  @Autowired
  private AccountRepo accountRepo;

  @Override
  public List<MindMapDto> findMindMapByUser(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Account account = accountRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    return mindMapMapper.toMindMapDto(account.getMindMap());
  }

  @Override
  public ResponseEntity<ByteArrayResource> findMindMapById(String rawId) {
    try {
      Long id = Long.parseLong(rawId);
      MindMap mindMap = mindMapRepo.findById(id)
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, MindMapConfig.NOT_FOUND));
      return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
          .body(new ByteArrayResource(mindMap.getData()));
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MindMapConfig.INVALID_ID);
    }
  }

  @Override
  public List<MindMapDto> findMindMapByTitle(String title) {
    List<MindMap> mindMap = mindMapRepo.findByTitle(title);
    return mindMapMapper.toMindMapDto(mindMap);
  }

  @Override
  public void addMindMap(MindMapDto mindMapDto) {
    try {
      MindMap mindMap = mindMapMapper.toMindMap(mindMapDto);

      // Add mindmap to account
      Account account = mindMap.getAccount();
      List<MindMap> accountMindMap = account.getMindMap();
      if (accountMindMap != null) {
        if (accountMindMap.stream()
            .noneMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMap.getTitle()))) {
          accountMindMap.add(mindMap);
        } else {
          throw new ResponseStatusException(HttpStatus.CONFLICT, MindMapConfig.CONFLICT);
        }
      } else {
        accountMindMap = Arrays.asList(mindMap);
      }

      mindMapRepo.save(mindMap);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          MindMapConfig.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void updateMindMap(String rawId, MindMapDto mindMapDto) {
    try {
      Long id = Long.parseLong(rawId);

      MindMap mindMap = mindMapRepo.findById(id)
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, MindMapConfig.NOT_FOUND));
      MindMap newMindMap = mindMapMapper.toMindMap(mindMapDto);

      // Update name
      Account account = mindMap.getAccount();
      List<MindMap> accountMindMap = account.getMindMap();
      if (accountMindMap.stream()
          .anyMatch(mindMapTemp -> mindMapTemp.getTitle().equals(mindMap.getTitle()))) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, MindMapConfig.CONFLICT);
      }
      mindMap.setTitle(newMindMap.getTitle());

      // Update data
      mindMap.setData(newMindMap.getData());

      mindMapRepo.save(mindMap);
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MindMapConfig.INVALID_ID);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          MindMapConfig.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void deleteMindMap(String rawId) {
    try {
      Long id = Long.parseLong(rawId);
      mindMapRepo.deleteById(id);
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MindMapConfig.INVALID_ID);
    }
  }
}
