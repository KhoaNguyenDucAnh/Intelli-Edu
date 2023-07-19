package com.intelliedu.intelliedu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.intelliedu.intelliedu.repository.CommentRepo;
import com.intelliedu.intelliedu.service.CommentService;

/** CommentServiceImpl */
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepo commentRepo;

}
