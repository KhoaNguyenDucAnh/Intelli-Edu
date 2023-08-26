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

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.mapper.DocumentMapper;
import com.intelliedu.intelliedu.repository.DocumentRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * DocumentService
 */
@Service
public class DocumentService {

	@Autowired
	private DocumentRepo documentRepo;

	@Autowired
	private DocumentMapper documentMapper;

	@Autowired
	private AuthService authService;

	public Map<String, Page<DocumentDto>> findDocument(String query, Authentication authentication, Pageable pageable) {
    if (authentication == null) {
      return Map.of("other", documentMapper.toDocumentDto(documentRepo.findByTitle(query, pageable)));
    } else {
      Account account = authService.getAccount(authentication);
      return Map.of(
        "other", documentMapper.toDocumentDto(documentRepo.findByTitleAndAccountIsNot(query, account, pageable)),
        "own", documentMapper.toDocumentDto(documentRepo.findByTitleAndAccount(query, account, pageable))
      );
    }
  }

  public DocumentDto findDocument(Long id, Authentication authentication) {
    return documentMapper.toDocumentDto(documentRepo
        .findByIdAndAccount(id, authService.getAccount(authentication))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
  }
  
  public DocumentDto createDocument(DocumentDto documentDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

		if (documentRepo.findByTitleAndAccount(documentDto.getTitle(), account).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		Document document = documentMapper.toDocument(documentDto);

    document.setAccount(account);
		
		account.getPost().add(document);

    return documentMapper.toDocumentDto(documentRepo.save(document));
  }

	public DocumentDto updateDocument(DocumentDto documentDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

    if (documentRepo.findByIdIsNotAndTitleAndAccount(documentDto.getPostDto().getId(), documentDto.getTitle(), account).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

		Document document = documentRepo
			.findByIdAndAccount(documentDto.getPostDto().getId(), account)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    document.setTitle(documentDto.getTitle());
    document.setContent(documentDto.getContent());

    return documentMapper.toDocumentDto(documentRepo.save(document));
  }

	@Transactional
  public void deleteDocument(Long id, Authentication authentication) {
    documentRepo.deleteByIdAndAccount(id, authService.getAccount(authentication));
  }
}
