package com.example.hh99_week5.service;

import com.example.hh99_week5.dto.MyCommentsDto;
import com.example.hh99_week5.dto.MyPostDto;
import com.example.hh99_week5.entity.*;
import com.example.hh99_week5.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;

    @Transactional(readOnly = true)
    public List<MyPostDto> readMyPosts(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        List<Post> posts = postRepository.findAllByMember(member);
        List<MyPostDto> myPosts = new ArrayList<>();
        for(Post post : posts){
            myPosts.add(MyPostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .likesCnt(likesRepository.findAllByPost(post).size())
                    .modifiedAt(post.getModifiedAt())
                    .createdAt(post.getCreatedAt())
                    .build());
        }
        return myPosts;
    }

    @Transactional(readOnly = true)
    public List<MyCommentsDto> readMyComments(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        List<Comment> comments = commentRepository.findAllByMember(member);
        List<SubComment> subComments = subCommentRepository.findAllByMember(member);
        List<MyCommentsDto> myComments = new ArrayList<>();
        for(Comment comment : comments){
            myComments.add(MyCommentsDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .likesCnt(likesRepository.findAllByComment(comment).size())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build());
        }
        for(SubComment subComment : subComments){
            myComments.add(MyCommentsDto.builder()
                    .id(subComment.getId())
                    .content(subComment.getContent())
                    .author(subComment.getAuthor())
                    .likesCnt(likesRepository.findAllBySubComment(subComment).size())
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .build());
        }
        return myComments;
    }

    @Transactional(readOnly = true)
    public List<MyPostDto> readMyPostsLikes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        List<Likes> likesList = likesRepository.findAllByMemberAndPostIsNotNull(member);
        List<Post> myPostsLikes = new ArrayList<>();
        for(Likes likes : likesList){
            myPostsLikes.add(likes.getPost());
        }
        List<MyPostDto> myPostLikesDtos = new ArrayList<>();
        for(Post post : myPostsLikes){
            myPostLikesDtos.add(MyPostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .likesCnt(likesRepository.findAllByPost(post).size())
                    .modifiedAt(post.getModifiedAt())
                    .createdAt(post.getCreatedAt())
                    .build());
        }
        return myPostLikesDtos;
    }

    @Transactional(readOnly = true)
    public List<MyCommentsDto> readMyCommentsLikes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(username);
        List<Likes> likesList = likesRepository.findAllByMemberAndCommentIsNotNull(member);
        List<Likes> likesList2 = likesRepository.findAllByMemberAndSubCommentIsNotNull(member);
        List<Comment> myCommentsLikes = new ArrayList<>();
        for(Likes likes : likesList){
            myCommentsLikes.add(likes.getComment());
        }
        List<MyCommentsDto> myCommentsLikesDtos = new ArrayList<>();
        for(Comment comment : myCommentsLikes ){
            myCommentsLikesDtos.add(MyCommentsDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .likesCnt(likesRepository.findAllByComment(comment).size())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build());
        }
        List<SubComment> mySubCommentLikes = new ArrayList<>();
        for(Likes likes : likesList2){
            mySubCommentLikes.add(likes.getSubComment());
        }
        for(SubComment subComment : mySubCommentLikes){
            myCommentsLikesDtos.add(MyCommentsDto.builder()
                    .id(subComment.getId())
                    .content(subComment.getContent())
                    .author(subComment.getAuthor())
                    .likesCnt(likesRepository.findAllBySubComment(subComment).size())
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .build());
        }
        return myCommentsLikesDtos;
    }
}
