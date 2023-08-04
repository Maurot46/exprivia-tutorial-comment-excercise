package com.tutorial.Tutorial.comments.controllers;

import com.tutorial.Tutorial.comments.entities.Comment;
import com.tutorial.Tutorial.comments.entities.Tutorial;
import com.tutorial.Tutorial.comments.repositories.CommentRepository;
import com.tutorial.Tutorial.comments.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api")
public class TutorialController {
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "/tutorials")
    private @ResponseBody List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    @PostMapping(path = "/tutorials")
    private ResponseEntity<Tutorial> createTutorial(
            @RequestBody Tutorial tutorial
    ) {
        Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), true));
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    }

    @GetMapping(path = "/tutorial/{id}")
    private @ResponseBody Tutorial findById(@PathVariable("id") long id) {
        return tutorialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND")
        );
    }

    @PutMapping(path = "/tutorials/{id}")
    private ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id,
                                                    @RequestBody Tutorial tutorial) {
        Tutorial _t = tutorialRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND")
        );
        _t.setTitle(tutorial.getTitle());
        _t.setDescription(tutorial.getDescription());
        _t.setPublished(tutorial.isPublished());
        return new ResponseEntity<>(tutorialRepository.save(_t), HttpStatus.OK);
    }

    @DeleteMapping(path = "/tutorial/{id}")
    private ResponseEntity<HttpStatus> deleteTutorialById(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

