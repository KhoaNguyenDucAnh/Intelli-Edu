package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.exception.AlreadyExistsException;
import com.intelliedu.intelliedu.exception.NotFoundException;
import com.intelliedu.intelliedu.mapper.FileMapper;
import com.intelliedu.intelliedu.repository.FileRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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

  public Page<FileDto> findFile(String title, Authentication authentication, Pageable pageable) {
    return fileMapper.toFileDto(fileRepo.findByTitleAndAccount(title, authService.getAccount(authentication), pageable));
  }

  public FileDto findFile(String id, Authentication authentication) {
    FileDto fileDto = fileMapper.toFileDto(findFileHelper(id, authentication));
    
    fileDto.setDocumentDto(documentService.findContent(id));
    fileDto.setMindMapDto(mindMapService.findContent(id));
    fileDto.setQuestionDto(questionService.findContent(id));
    
    return fileDto;
  }

  private File findFile(String id, Account account) {
    return fileRepo
      .findByIdAndAccount(id, account)
      .orElseThrow(() -> new NotFoundException(File.class, id));
  }

  public File findFileHelper(String id, Authentication authentication) {
    return findFile(id, authService.getAccount(authentication));
  }
    
  public FileDto createFile(FileDto fileDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (fileRepo.existsByTitleAndAccount(fileDto.getTitle(), account)) {
      throw new AlreadyExistsException(File.class, "title", fileDto.getTitle());
    }

    File file = fileMapper.toFile(fileDto);
    file.setAccount(account);

    return fileMapper.toFileDto(fileRepo.save(file));
  }

  public FileDto updateFile(String id, FileDto fileDto, Authentication authentication) {
    return fileMapper.toFileDto(fileRepo.save(fileMapper.toFile(fileDto, findFileHelper(id, authentication))));
  }

  @Transactional
  public void deleteFile(String id, Authentication authentication) {
    if (!fileRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(File.class, id);
    }

    fileRepo.deleteById(id);
    documentService.deleteContent(id);
    mindMapService.deleteContent(id);
    questionService.deleteContent(id);
  }

  public FileDto addSharedContent(String id, Authentication authentication) {
    Account account = authService.getAccount(authentication);
    
    File file = findFile(id, account);
    
    entityManager.detach(file);
    file.setAccount(account);
    account.getFile().add(file);

    return fileMapper.toFileDto(file);
  }
}
