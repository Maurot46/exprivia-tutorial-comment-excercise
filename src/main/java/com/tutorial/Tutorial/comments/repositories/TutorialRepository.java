package com.tutorial.Tutorial.comments.repositories;

import com.tutorial.Tutorial.comments.entities.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByTitleContaining(String title);


}
