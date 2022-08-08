package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.Comment;
import com.example.hh99_week5.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMember(Member member);
}
