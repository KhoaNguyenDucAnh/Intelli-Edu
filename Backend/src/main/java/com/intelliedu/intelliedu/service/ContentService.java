package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.ContentDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Content;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.mapper.ContentMapper;
import com.intelliedu.intelliedu.repository.ContentRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

import jakarta.transaction.Transactional;

/**
 * ContentService
 */
public abstract class ContentService<C extends Content, CDto extends ContentDto, CMapper extends ContentMapper<C, CDto>> {

  @Autowired
  private ContentRepo<C> contentRepo;

  @Autowired
  private CMapper contentMapper;

  @Autowired
  private FileService fileService;

  @Autowired
	private AuthService authService;

  public Map<String, Page<CDto>> findContent(String query, Authentication authentication, Pageable pageable) {
    if (authentication == null) {
      return Map.of("other", contentMapper.toDto(contentRepo.findByKeyword(query, pageable)));
    } else {
      Account account = authService.getAccount(authentication);
      return Map.of(
        "other", contentMapper.toDto(contentRepo.findByKeywordAndFileAccountIsNot(query, account, pageable)),
        "own", contentMapper.toDto(contentRepo.findByKeywordAndFileAccount(query, account, pageable))
      );
    }
  }

  public CDto findContent(String token, Authentication authentication) {
    return contentMapper.toDto(
      contentRepo
        .findByFileTokenAndFileAccount(token, authService.getAccount(authentication))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
    );
  }

  public CDto createContent(String token, Authentication authentication) {
    return contentMapper.toDto(contentRepo.save(createContent(fileService.findFile(token, authentication))));
  }

  protected abstract C createContent(File file);

  public CDto updateContent(String token, CDto contentDto, Authentication authentication) {
    return contentMapper.toDto(
      contentRepo.save(
        contentMapper.toEntity(
          contentDto, 
          contentRepo
			      .findByFileTokenAndFileAccount(token, authService.getAccount(authentication))
			      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        )
      )
    );
  }

  @Transactional
  public void deleteContent(String token, Authentication authentication) {
    contentRepo.deleteByFileTokenAndFileAccount(token, authService.getAccount(authentication));
  }

  public void shareContent(String token, Authentication authentication) {
    contentRepo
      .findByFileTokenAndFileAccount(token, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
    .setIsShared(true)  ;
  }
}
