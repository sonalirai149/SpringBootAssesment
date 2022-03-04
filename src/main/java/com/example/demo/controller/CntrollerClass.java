package com.example.demo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.repository.Repository;
import com.example.demo.model.EntityClass;

@CrossOrigin(origins = "http://localhost:9098")
@RestController
@RequestMapping("/api")
public class CntrollerClass {
	@Autowired
	  Repository entityRepository;
	  @GetMapping("/myEntity")
	  public ResponseEntity<List<EntityClass>> getAllTutorials(@RequestParam(required = false) String title) {
	    try {
	      List<EntityClass> tutorials = new ArrayList<EntityClass>();
	      if (title == null)
	        entityRepository.findAll().forEach(tutorials::add);
	      else
	        entityRepository.findByTitleContaining(title).forEach(tutorials::add);
	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @GetMapping("/tutorials/{id}")
	  public ResponseEntity<EntityClass> getTutorialById(@PathVariable("id") long id) {
	    Optional<EntityClass> tutorialData = entityRepository.findById(id);
	    if (tutorialData.isPresent()) {
	      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  @PostMapping("/tutorials")
	  public ResponseEntity<EntityClass> createTutorial(@RequestBody EntityClass entity) {
	    try {
	    	EntityClass _entity = entityRepository
	          .save(new EntityClass(entity.getTitle(), entity.getDescription(), false));
	      return new ResponseEntity<>(_entity, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @PutMapping("/tutorials/{id}")
	  public ResponseEntity<EntityClass> updateTutorial(@PathVariable("id") long id, @RequestBody EntityClass entity) {
	    Optional<EntityClass> tutorialData = entityRepository.findById(id);
	    if (tutorialData.isPresent()) {
	      EntityClass _tutorial = tutorialData.get();
	      _tutorial.setTitle(entity.getTitle());
	      _tutorial.setDescription(entity.getDescription());
	      _tutorial.setPublished(entity.isPublished());
	      return new ResponseEntity<>(entityRepository.save(_tutorial), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  @DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
	    try {
	      entityRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @DeleteMapping("/tutorials")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    try {
	      entityRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  @GetMapping("/tutorials/published")
	  public ResponseEntity<List<EntityClass>> findByPublished() {
	    try {
	      List<EntityClass> tutorials = entityRepository.findByPublished(true);
	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
