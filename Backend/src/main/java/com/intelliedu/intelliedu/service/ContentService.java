package com.intelliedu.intelliedu.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.intelliedu.intelliedu.dto.ContentDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Content;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.entity.Post;
import com.intelliedu.intelliedu.exception.AlreadyExistsException;
import com.intelliedu.intelliedu.exception.NotFoundException;
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
        "other", contentMapper.toDto(contentRepo.findByKeywordAndAccountIsNotAndSharedIsTrue(query, account, pageable)),
        "own", contentMapper.toDto(contentRepo.findByKeywordAndAccount(query, account, pageable))
      );
    }
  }

  public CDto findContent(UUID id) {
    return contentMapper.toDto(contentRepo.findById(id).orElse(null));
  }

  protected C findContentHelper(UUID id) {
    return contentRepo
      .findById(id)
      .orElseThrow(() -> new NotFoundException(getGenericClass(), id));
  }

  protected C findContentHelper(UUID id, Account account) {
    return contentRepo
      .findByIdAndAccount(id, account)
      .orElseThrow(() -> new NotFoundException(getGenericClass(), id));
  }

  protected C findContentHelper(UUID id, Authentication authentication) {
    return findContentHelper(id, authService.getAccount(authentication));
  }

  public boolean isShared(UUID id) {
    return contentRepo.existsByIdAndSharedIsTrue(id);
  }

  protected abstract Class<C> getGenericClass();

  protected abstract C createContent(String title);
 
  private CDto saveContent(C content) {
    return contentMapper.toDto(contentRepo.save(content));
  }
  
  public CDto createContent(UUID id, Authentication authentication) {
    if (contentRepo.existsById(id)) {
      throw new AlreadyExistsException(getGenericClass(), id);
    }

    Account account = authService.getAccount(authentication);

    File file = fileService.findFileHelper(id, account); 

    C content = createContent(file.getTitle());
    content.getKeyword().add(file.getTitle());
    content.setFile(file);
    content.setPost(Post.builder().account(account).build());

    return saveContent(content);
  }

  public CDto updateContent(UUID id, CDto contentDto, Authentication authentication) {
    return saveContent(contentMapper.toEntity(contentDto, findContentHelper(id, authentication)));
  }

  @Transactional
  public void deleteContent(UUID id, Authentication authentication) {
    if (!contentRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(getGenericClass(), id);
    }
    deleteContent(id);
  }

  @Transactional
  public void deleteContent(UUID id) {
    contentRepo.deleteById(id);
  }

  public void shareContent(UUID id) {
    C content = findContentHelper(id);
    content.setShared(true);
    contentRepo.save(content);
  }

  public void unshareContent(UUID id) {
    C content = findContentHelper(id);
    content.setShared(true);
    contentRepo.save(content);
  }
}
