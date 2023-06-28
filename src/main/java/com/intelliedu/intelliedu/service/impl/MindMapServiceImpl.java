package com.intelliedu.intelliedu.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.entity.User;
import com.intelliedu.intelliedu.mapper.MindMapMapper;
import com.intelliedu.intelliedu.repository.MindMapRepository;
import com.intelliedu.intelliedu.service.MindMapService;

@Service
public class MindMapServiceImpl implements MindMapService {

  @Autowired
  private MindMapMapper mindMapMapper;

  @Autowired
  private MindMapRepository mindMapRepository;

  @Override
  public ResponseEntity<ByteArrayResource> findMindMapById(String rawId) {
    try {
      Long id = Long.parseLong(rawId);
      MindMap mindMap = mindMapRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN)
          .body(new ByteArrayResource(mindMap.getData()));
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public List<MindMapDto> findMindMapByName(String name) {
    List<MindMap> mindMap = mindMapRepository.findByName(name);
    return mindMapMapper.toMindMapDto(mindMap);
  }

  @Override
  public void addMindMap(MindMapDto mindMapDto) {
    try {
      MindMap mindMap = mindMapMapper.toMindMap(mindMapDto);

      // Add mindmap to user
      User user = mindMap.getUser();
      List<MindMap> userMindMap = user.getMindMap();
      if (userMindMap != null) {
        if (userMindMap.stream()
            .noneMatch(mindMapTemp -> mindMapTemp.getName().equals(mindMap.getName()))) {
          userMindMap.add(mindMap);
        } else {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
      } else {
        userMindMap = Arrays.asList(mindMap);
      }

      mindMapRepository.save(mindMap);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void updateMindMap(String rawId, MindMapDto mindMapDto) {
    try {
      Long id = Long.parseLong(rawId);

      MindMap mindMap = mindMapRepository.findById(id)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
      MindMap newMindMap = mindMapMapper.toMindMap(mindMapDto);

      // Update name
      User user = mindMap.getUser();
      List<MindMap> userMindMap = user.getMindMap();
      if (userMindMap.stream()
          .anyMatch(mindMapTemp -> mindMapTemp.getName().equals(mindMap.getName()))) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
      mindMap.setName(newMindMap.getName());

      // Update data
      mindMap.setData(newMindMap.getData());

      mindMapRepository.save(mindMap);
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public void deleteMindMap(String rawId) {
    try {
      Long id = Long.parseLong(rawId);
      mindMapRepository.deleteById(id);
    } catch (NumberFormatException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
