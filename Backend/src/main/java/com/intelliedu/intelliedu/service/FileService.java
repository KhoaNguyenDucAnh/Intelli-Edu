package com.intelliedu.intelliedu.service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.exception.AlreadyExistsException;
import com.intelliedu.intelliedu.exception.NotFoundException;
import com.intelliedu.intelliedu.mapper.FileMapper;
import com.intelliedu.intelliedu.repository.FileRepo;
import com.intelliedu.intelliedu.repository.MindMapRepo;
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

  @Autowired
  private MindMapRepo mindMapRepo;

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

  @Autowired
  private AIService aiService;

  @Value("${domain}")
  private String domain;

  public Page<FileDto> findFile(String title, Authentication authentication, Pageable pageable) {
    return fileMapper.toFileDto(fileRepo.findByTitleAndAccount(title, authService.getAccount(authentication), pageable));
  }

  public FileDto findFile(UUID id, Authentication authentication) {
    FileDto fileDto = fileMapper.toFileDto(findFileHelper(id, authentication));
    fileDto.setDocument(documentService.findContent(id));
    fileDto.setMindMap(mindMapService.findContent(id));
    fileDto.setQuestion(questionService.findContent(id));
    return fileDto;
  }

  public File findFileHelper(UUID id) {
    return fileRepo.findById(id).orElseThrow(() -> new NotFoundException(File.class, id));
  }

  public File findFileHelper(UUID id, Authentication authentication) {
    return findFileHelper(id, authService.getAccount(authentication));
  }

  public File findFileHelper(UUID id, Account account) {
    return fileRepo.findByIdAndAccount(id, account).orElseThrow(() -> new NotFoundException(File.class, id.toString()));
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

  public FileDto updateFile(UUID id, FileDto fileDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (fileRepo.existsByTitleAndIdIsNotAndAccount(fileDto.getTitle(), id, account)) {
      throw new AlreadyExistsException(File.class, "title", fileDto.getTitle());
    }

    return fileMapper.toFileDto(fileRepo.save(fileMapper.toFile(fileDto, findFileHelper(id, account))));
  }

  @Transactional
  public void deleteFile(UUID id, Authentication authentication) {
    if (!fileRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(File.class, id);
    }

    fileRepo.deleteById(id);
    documentService.deleteContent(id);
    mindMapService.deleteContent(id);
    questionService.deleteContent(id);
  }

  public String shareContent(UUID id, boolean documentShare, boolean mindMapShare, boolean questionShare, Authentication authentication) {
    if (!fileRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(File.class, id);
    }
    return "http://" + domain + "/share/file/" + id;
  }

  public FileDto addSharedContent(UUID id, Authentication authentication) {
    if (!(documentService.isShared(id) || mindMapService.isShared(id) || questionService.isShared(id))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    
    File file = findFileHelper(id);
    
    entityManager.detach(file);
    
    file.setAccount(authService.getAccount(authentication));

    return fileMapper.toFileDto(file);
  }

  public String checkMindMap(UUID id, Authentication authentication) {
    if (!fileRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(File.class, id);
    }

    Document document = documentService.findContentHelper(id);
    MindMap mindMap = mindMapService.findContentHelper(id);

    if (document.getPreContent().equals(document.getContent()) && mindMap.getPreContent().equals(mindMap.getPreContent())) {
      return mindMap.getFeedback();
    } else {
      String feedback = aiService.request(documentService.findContentHelper(id), mindMapService.findContentHelper(id));

      document.setPreContent(document.getPreContent());
      mindMap.setPreContent(mindMap.getContent());
      mindMap.setFeedback(feedback);
      mindMapRepo.save(mindMap);

      return feedback;
    }
  }

  /*public CompletableFuture<String> createQuestion(UUID id, Authentication authentication) {
    if (fileRepo.existsByIdAndAccount(id, authService.getAccount(authentication))) {
      throw new NotFoundException(File.class, id);
    }
    return aiService.request(documentService.findContentHelper(id));
  }*/
}
