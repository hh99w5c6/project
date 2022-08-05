package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
