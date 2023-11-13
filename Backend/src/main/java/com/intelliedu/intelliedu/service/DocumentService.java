package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.entity.Document;

/**
 * DocumentService
 */
@Service
public class DocumentService extends ContentService<Document, DocumentDto> {

  @Override
  protected Document createContent(String title) {
    return Document.builder().content("").build(); 
  }

  @Override
  protected Class<Document> getGenericClass() {
    return Document.class;
  }
}
