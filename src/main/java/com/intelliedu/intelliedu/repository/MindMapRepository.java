package com.intelliedu.intelliedu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intelliedu.intelliedu.entity.MindMap;
import com.intelliedu.intelliedu.entity.User;


public interface MindMapRepository extends JpaRepository<MindMap, Long> {

  List<MindMap> findByName(String name);

  List<MindMap> findByUser(User user);
}
