package com.intelliedu.intelliedu.service;

import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.dto.DocumentDto;
import com.intelliedu.intelliedu.entity.Document;
import com.intelliedu.intelliedu.mapper.DocumentMapper;

/**
 * DocumentService
 */
@Service
public class DocumentService extends ContentService<Document, DocumentDto, DocumentMapper> {

}
