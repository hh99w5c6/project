package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
