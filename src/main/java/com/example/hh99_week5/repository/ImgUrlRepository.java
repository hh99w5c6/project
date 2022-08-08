package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.ImgUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgUrlRepository extends JpaRepository<ImgUrl, Long> {
    boolean existsByFileName(String filename);
}
