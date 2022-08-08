package com.example.hh99_week5.repository;

import com.example.hh99_week5.entity.SubComment;
import com.example.hh99_week5.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByMember(Member member);
}
