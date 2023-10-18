package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.entity.File;
import com.intelliedu.intelliedu.mapper.DocumentMapper;

/**
 * DocumentService
 */
@Service
public class DocumentService extends ContentService<Document, DocumentDto, DocumentMapper> {

  @Override
  protected Document createContent(File file) {
    if (file.getDocument() != null) {
      return file.getDocument();
    }

    Document document = new Document();

    file.setDocument(document);
    document.setFile(file);
    document.setId(file.getId());

    return document;
  }
}
