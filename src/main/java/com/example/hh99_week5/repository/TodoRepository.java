package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
