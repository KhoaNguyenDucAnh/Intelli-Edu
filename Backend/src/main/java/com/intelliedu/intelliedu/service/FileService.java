package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.mapper.FileMapper;
import com.intelliedu.intelliedu.repository.FileRepo;
import com.intelliedu.intelliedu.security.service.AuthService;
import com.intelliedu.intelliedu.util.HashUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * FileService
 */
public class FileService {

  @Autowired
  private FileMapper fileMapper;

  @Autowired
  private FileRepo fileRepo;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
	private AuthService authService;

  public File findFile(String token, Authentication authentication) {
    return fileRepo
      .findByTokenAndAccount(token, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public FileDto findFile(File file) {
    return fileMapper.toFileDto(file);
  }

  public File createFile(FileDto fileDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (fileRepo.existsByTitleAndAccount(fileDto.getTitle(), account)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    File file = fileMapper.toFile(fileDto);

    file.setToken(HashUtil.timeBasedHash());
    file.setAccount(account);
    account.getFile().add(file);

    return fileRepo.save(file);
  }

  public FileDto createFile(File file) {
    return fileMapper.toFileDto(file);
  }

  public FileDto addSharedContent(String token, Authentication authentication) {
    File file = fileRepo
      .findByToken(token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    entityManager.detach(file);

    if (!file.getDocument().getIsShared()) {
      file.setDocument(null);
    }
    if (!file.getMindMap().getIsShared()) {
      file.setMindMap(null);
    }
    if (!file.getQuestion().getIsShared()) {
      file.setQuestion(null);
    }
    
    Account account = authService.getAccount(authentication);
    account.getFile().add(file);
    file.setAccount(account);

    return fileMapper.toFileDto(file);
  }
}
