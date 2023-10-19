package com.intelliedu.intelliedu.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.mapper.FileMapper;
import com.intelliedu.intelliedu.repository.FileRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * FileService
 */
@Service
public class FileService {

  @Autowired
  private FileMapper fileMapper;

  @Autowired
  private FileRepo fileRepo;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
	private AuthService authService;

  @Autowired
  private DocumentService documentService;

  @Autowired
  private MindMapService mindMapService;

  @Autowired
  private QuestionService questionService;

  public FileDto findFile(String id, Authentication authentication) {
    FileDto fileDto = fileMapper.toFileDto(findFileHelper(id, authentication));

    fileDto.setDocumentDto(documentService.findContent(id));
    fileDto.setMindMapDto(mindMapService.findContent(id));
    fileDto.setQuestionDto(questionService.findContent(id));

    return fileDto;
  }

  public File findFileHelper(String id, Authentication authentication) {
    return fileRepo
      .findByIdAndAccount(id, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
    
  public FileDto createFile(FileDto fileDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (fileRepo.existsByTitleAndAccount(fileDto.getTitle(), account)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    File file = fileMapper.toFile(fileDto);

    file.setId(UUID.randomUUID().toString());
    file.setAccount(account);

    return fileMapper.toFileDto(fileRepo.save(file));
  }

  public FileDto addSharedContent(String id, Authentication authentication) {
    File file = fileRepo
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    entityManager.detach(file);
    
    Account account = authService.getAccount(authentication);
    account.getFile().add(file);
    file.setAccount(account);

    return fileMapper.toFileDto(file);
  }
}
