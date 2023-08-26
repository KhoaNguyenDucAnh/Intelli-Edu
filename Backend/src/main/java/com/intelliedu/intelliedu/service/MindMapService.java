package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.MindMapDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.mapper.MindMapMapper;
import com.intelliedu.intelliedu.repository.MindMapRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

@Service
public class MindMapService {

  @Autowired
  private MindMapRepo mindMapRepo;

  @Autowired
  private MindMapMapper mindMapMapper;

  @Autowired
  private AuthService authService;
  
  public Map<String, Page<MindMapDto>> findMindMap(String query, Authentication authentication, Pageable pageable) {
    if (authentication == null) {
      return Map.of("other", mindMapMapper.toMindMapDto(mindMapRepo.findByTitle(query, pageable))); 
    } else {
      Account account = authService.getAccount(authentication);
      return Map.of(
        "other", mindMapMapper.toMindMapDto(mindMapRepo.findByTitleAndAccountIsNot(query, account, pageable)),
        "own", mindMapMapper.toMindMapDto(mindMapRepo.findByTitleAndAccount(query, account, pageable))
      );
    }
  }

  public MindMapDto findMindMap(Long id, Authentication authentication) {
    return mindMapMapper.toMindMapDto(mindMapRepo
        .findByIdAndAccount(id, authService.getAccount(authentication))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }
  
  public MindMapDto createMindMap(MindMapDto mindMapDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

		if (mindMapRepo.findByTitleAndAccount(mindMapDto.getTitle(), account).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		MindMap mindMap = mindMapMapper.toMindMap(mindMapDto);

    mindMap.setAccount(account);
		
		account.getPost().add(mindMap);

    return mindMapMapper.toMindMapDto(mindMapRepo.save(mindMap));
  }
  
  public MindMapDto updateMindMap(MindMapDto mindMapDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

		if (mindMapRepo.findByIdIsNotAndTitleAndAccount(mindMapDto.getPostDto().getId(), mindMapDto.getTitle(), account).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    MindMap mindMap = mindMapRepo
			.findByIdAndAccount(mindMapDto.getPostDto().getId(), account)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		mindMap.setTitle(mindMapDto.getTitle());
    mindMap.setContent(mindMapDto.getContent());

    return mindMapMapper.toMindMapDto(mindMapRepo.save(mindMap));
  }

	@Transactional
  public void deleteMindMap(Long id, Authentication authentication) {
    mindMapRepo.deleteByIdAndAccount(id, authService.getAccount(authentication));
  }
}
