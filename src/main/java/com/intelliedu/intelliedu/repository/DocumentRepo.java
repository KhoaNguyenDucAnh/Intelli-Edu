package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intelliedu.intelliedu.entity.Document;

public interface DocumentRepo extends JpaRepository<Document, Long> {

}
