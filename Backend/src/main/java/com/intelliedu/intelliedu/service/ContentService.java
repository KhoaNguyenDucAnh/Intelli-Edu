package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public abstract class ContentService<C extends Content, CDto extends ContentDto> {

  @Autowired
  private ContentRepo<C> contentRepo;

  @Autowired
  private ContentMapper<C, CDto> contentMapper;

  @Autowired
  @Lazy
  private FileService fileService;

  @Autowired
	private AuthService authService;

  public Map<String, Page<CDto>> findContent(String query, Authentication authentication, Pageable pageable) {
    if (authentication == null) {
      return Map.of("other", contentMapper.toDto(contentRepo.findByKeyword(query, pageable)));
    } else {
      Account account = authService.getAccount(authentication);
      return Map.of(
        "other", contentMapper.toDto(contentRepo.findByKeywordAndFileAccountIsNotAndIsSharedIsTrue(query, account, pageable)),
        "own", contentMapper.toDto(contentRepo.findByKeywordAndFileAccount(query, account, pageable))
      );
    }
  }

  public CDto findContent(String id) {
    return contentMapper.toDto(contentRepo.findById(id).orElse(null));
  }

  public CDto createContent(String id, Authentication authentication) {
    return contentMapper.toDto(contentRepo.save(createContent(fileService.findFileHelper(id, authentication))));
  }

  private C createContent(File file) {
    if (contentRepo.existsById(file.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    C content = createContent();

    content.addKeyword(file.getTitle());
    content.setFile(file);

    return content;
  }

  protected abstract C createContent();

  public CDto updateContent(String id, CDto contentDto, Authentication authentication) {
    return contentMapper.toDto(
      contentRepo.save(
        contentMapper.toEntity(
          contentDto, 
          contentRepo
			      .findByIdAndFileAccount(id, authService.getAccount(authentication))
			      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        )
      )
    );
  }

  @Transactional
  public void deleteContent(String id, Authentication authentication) {
    contentRepo.deleteByIdAndFileAccount(id, authService.getAccount(authentication));
  }

  public void shareContent(String id, Authentication authentication) {
    contentRepo
      .findByIdAndFileAccount(id, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
    .setIsShared(true)  ;
  }
}
