package com.example.hh99_week5.service;

import com.example.hh99_week5.entity.*;
import com.example.hh99_week5.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;

    @Transactional
    public void likesToPost(Long postId){ // 게시글에 좋아요
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if(!likesRepository.existsByMemberAndPost(member,post)){
            likesRepository.save(Likes.builder()
                    .member(member)
                    .post(post)
                    .build());
        } else{
            likesRepository.deleteByMemberAndPost(member, post);
        }
    }

    @Transactional
    public void likesToComment(Long commentId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다"));
        if(!likesRepository.existsByMemberAndComment(member,comment)){
            likesRepository.save(Likes.builder()
                    .member(member)
                    .comment(comment)
                    .build());
        } else{
            likesRepository.deleteByMemberAndComment(member, comment);
        }
    }

    @Transactional
    public void likesToSubComment(Long subCommentId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        SubComment subComment = subCommentRepository.findById(subCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다"));
        if(!likesRepository.existsByMemberAndSubComment(member, subComment)){
            likesRepository.save(Likes.builder()
                    .member(member)
                    .subComment(subComment)
                    .build());
        } else{
            likesRepository.deleteByMemberAndSubComment(member, subComment);
        }
    }
}
