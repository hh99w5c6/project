package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes ,Long> {
    boolean existsByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);

    boolean existsByMemberAndComment(Member member, Comment comment);
    void deleteByMemberAndComment(Member member, Comment comment);

    boolean existsByMemberAndSubComment(Member member, SubComment subComment);
    void deleteByMemberAndSubComment(Member member, SubComment subComment);

    List<Likes> findAllByPost(Post post);
    List<Likes> findAllByComment(Comment comment);
    List<Likes> findAllBySubComment(SubComment subComment);
    List<Likes> findAllByMemberAndPostIsNotNull(Member member);
    List<Likes> findAllByMemberAndCommentIsNotNull(Member member);
    List<Likes> findAllByMemberAndSubCommentIsNotNull(Member member);
}
