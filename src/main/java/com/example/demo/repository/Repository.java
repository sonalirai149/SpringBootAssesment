package com.example.demo.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.EntityClass;
public interface Repository extends JpaRepository<EntityClass, Long> {
	List<EntityClass> findByPublished(boolean published);
	  List<EntityClass> findByTitleContaining(String title);
}
