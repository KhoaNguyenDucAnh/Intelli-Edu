package com.intelliedu.intelliedu.service;

import java.util.Map;

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

  public CDto findContent(String id) {
    return contentMapper.toDto(contentRepo.findById(id).orElse(null));
  }

  protected C findContent(String id, Account account) {
    return contentRepo
      .findByIdAndAccount(id, account)
      .orElseThrow(() -> new NotFoundException(getGenericClass(), id));
  }

  protected C findContent(String id, Authentication authentication) {
    return findContent(id, authService.getAccount(authentication));
  }

  public boolean isShared(String id) {
    return contentRepo.existsByIdAndSharedIsTrue(id);
  }

  protected abstract Class<C> getGenericClass();

  protected abstract C createContent(String title);
 
  private CDto saveContent(C content) {
    return contentMapper.toDto(contentRepo.save(content));
  } 
  
  public CDto createContent(String id, Authentication authentication) {
    File file = fileService.findFileHelper(id, authentication); 

    if (contentRepo.existsById(file.getId())) {
      throw new AlreadyExistsException(getGenericClass(), "id", id);
    }

    C content = createContent(file.getTitle());
    content.getKeyword().add(file.getTitle());
    content.setFile(file);

    return saveContent(content);
  }

  public CDto updateContent(String id, CDto contentDto, Authentication authentication) {
    return saveContent(contentMapper.toEntity(contentDto, findContent(id, authentication)));
  }

  @Transactional
  public void deleteContent(String id, Authentication authentication) {
    if (!contentRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(getGenericClass(), id);
    }
    deleteContent(id);
  }

  @Transactional
  public void deleteContent(String id) {
    contentRepo.deleteById(id);
  }

  public void shareContent(String id, Authentication authentication) {
    Account account = authService.getAccount(authentication);
    
    C content = findContent(id, account);
    content.setShared(true);

    if (content.getPost() != null) {
      content.setPost(Post.builder().account(account).build());
    }
    
    contentRepo.save(content);
  }
}
