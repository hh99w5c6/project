package com.example.hh99_week5.service;

import com.example.hh99_week5.dto.CommentRequestDto;
import com.example.hh99_week5.entity.Comment;
import com.example.hh99_week5.entity.SubComment;
import com.example.hh99_week5.entity.Member;
import com.example.hh99_week5.entity.Post;
import com.example.hh99_week5.repository.CommentRepository;
import com.example.hh99_week5.repository.SubCommentRepository;
import com.example.hh99_week5.repository.MemberRepository;
import com.example.hh99_week5.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final SubCommentRepository subCommentRepository;

    @Transactional
    public ResponseEntity<String> createComment(Long postId, CommentRequestDto commentRequestDto) {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(author);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        commentRepository.save(Comment.builder()
                .author(author)
                .content(commentRequestDto.getContent())
                .member(member)
                .post(post)
                .build());
        return new ResponseEntity<>("댓글이 등록되었습니다", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> createSubComment(Long commentId, CommentRequestDto commentRequestDto){
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(author);

        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        subCommentRepository.save(SubComment.builder()
                .author(author)
                .content(commentRequestDto.getContent())
                .member(member)
                .comment(comment)
                .build());
        return new ResponseEntity<>("댓글이 등록되었습니다", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> updateComment(Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if(!comment.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        comment.updateComment(commentRequestDto);
        commentRepository.save(comment);
        return new ResponseEntity<>("댓글이 수정되었습니다", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateSubComment(Long subCommentId, CommentRequestDto commentRequestDto){
        SubComment subComment = subCommentRepository.findById(subCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if(!subComment.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        subComment.updateSubComment(commentRequestDto);
        subCommentRepository.save(subComment);
        return new ResponseEntity<>("댓글이 수정되었습니다", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if (!comment.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        commentRepository.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<String> deleteSubComment(Long subCommentId){
        SubComment subComment = subCommentRepository.findById(subCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if (!subComment.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        subCommentRepository.deleteById(subCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
