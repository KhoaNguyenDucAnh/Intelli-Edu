package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.repository.CommentRepo;
import com.intelliedu.intelliedu.service.CommentService;

/** CommentServiceImpl */
@Service
public class CommentService {

  @Autowired
  private CommentRepo commentRepo;

}
