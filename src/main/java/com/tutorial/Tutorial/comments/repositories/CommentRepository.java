package com.tutorial.Tutorial.comments.repositories;

import com.tutorial.Tutorial.comments.entities.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    List<Comment> findByTutorialId(Long id);
}
