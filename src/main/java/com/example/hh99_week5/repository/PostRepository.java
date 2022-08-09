package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.Member;
import com.example.hh99_week5.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMember(Member member);
    List<Post> findAllByCommentListIsNull();
}
