package com.intelliedu.intelliedu.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.ContentDto;
import com.intelliedu.intelliedu.dto.FileDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Content;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.mapper.ContentMapper;
import com.intelliedu.intelliedu.mapper.FileMapper;
import com.intelliedu.intelliedu.repository.ContentRepo;
import com.intelliedu.intelliedu.repository.FileRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

import jakarta.transaction.Transactional;

/**
 * ContentService
 */
public abstract class ContentService<C extends Content, CDto extends ContentDto, CMapper extends ContentMapper<C, CDto>> {

  @Autowired
  private ContentRepo<C> contentRepo;

  @Autowired
  private FileRepo fileRepo;

  @Autowired
  private CMapper contentMapper;

  @Autowired
  private FileMapper fileMapper;

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

  public FileDto findContent(Long id, Authentication authentication) {
    return fileMapper.toFileDto(contentRepo
      .findByIdAndFileAccount(id, authService.getAccount(authentication))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
      .getFile());
  }
  
  public FileDto createContent(FileDto fileDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (fileRepo.existsByTitleAndAccount(fileDto.getTitle(), account)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    File file = fileMapper.toFile(fileDto);

    file.setAccount(account);
    account.getFile().add(file);

    return fileMapper.toFileDto(
      contentRepo.save(createContent(file))
      .getFile()
    );
  }


  public FileDto createContent(Long fileId, Authentication authentication) {
    return fileMapper.toFileDto(
      contentRepo.save(
        createContent(fileRepo
          .findByIdAndAccount(fileId, authService.getAccount(authentication))
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST))
        )
      )
      .getFile()
    );
  }

  protected abstract C createContent(File file);

  public CDto updateContent(CDto contentDto, Authentication authentication) {
		C content = contentRepo
			.findByIdAndFileAccount(contentDto.getId(), authService.getAccount(authentication))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return contentMapper.toDto(contentRepo.save(contentMapper.toEntity(contentDto, content)));
  }


  @Transactional
  public void deleteContent(Long id, Authentication authentication) {
    contentRepo.deleteByIdAndFileAccount(id, authService.getAccount(authentication));
  }
}
