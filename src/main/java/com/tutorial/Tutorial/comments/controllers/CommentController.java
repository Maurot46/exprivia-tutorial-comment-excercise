package com.tutorial.Tutorial.comments.controllers;

import com.tutorial.Tutorial.comments.entities.Comment;
import com.tutorial.Tutorial.comments.repositories.CommentRepository;
import com.tutorial.Tutorial.comments.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TutorialRepository tutorialRepository;

    @PostMapping(path = "/tutorials/{id}/comments")
    public ResponseEntity<Comment> addCommentToPost(
            @PathVariable("id")long id,
            @RequestBody Comment commentRequest
    ){
        Comment comment = tutorialRepository.findById(id).map(tutorial -> {
            commentRequest.setTutorial(tutorial);
            return commentRepository.save(commentRequest);
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
        return new ResponseEntity<>(comment,HttpStatus.CREATED);
    }
    @GetMapping(path = "/comments/{id}")
    public @ResponseBody Comment getCommentById(
            @PathVariable(value ="id") long id){
        return commentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND")
        );
    }
    @GetMapping(path = "/tutorials/{id}/comments")
    public ResponseEntity<List<Comment>> getAllCommentFromPost(@PathVariable(value = "id")Long id){
        if(!tutorialRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        List<Comment> comment = commentRepository.findByTutorialId(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
    @DeleteMapping(path = "/comments/{id}")
    public ResponseEntity<Comment> deleteCommentById(@PathVariable long id){
        commentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
