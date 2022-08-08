package com.example.hh99_week5.service;

import com.example.hh99_week5.dto.*;
import com.example.hh99_week5.entity.*;
import com.example.hh99_week5.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final LikesRepository likesRepository;
    private final S3Uploader s3Uploader;

    @Transactional(readOnly = true)
    public List<PostsResponseDto> readPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postsResponseDtoList.add(PostsResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getAuthor())
                    .commentsCnt(post.getCommentList().size())
                    .likesCnt(likesRepository.findAllByPost(post).size())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build());
        }
        return postsResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto readPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : post.getCommentList()) {
            List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();
            for (SubComment subComment : comment.getSubCommentList()) {
                subCommentResponseDtoList.add(SubCommentResponseDto.builder()
                        .id(subComment.getId())
                        .content(subComment.getContent())
                        .author(subComment.getAuthor())
                        .likesCnt(likesRepository.findAllBySubComment(subComment).size())
                        .createdAt(subComment.getCreatedAt())
                        .modifiedAt(subComment.getModifiedAt())
                        .build());
            }
            commentResponseDtoList.add(CommentResponseDto.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .likesCnt(likesRepository.findAllByComment(comment).size())
                    .subCommentList(subCommentResponseDtoList)
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build());
        }

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .imgUrl(post.getImageUrl())
                .likesCnt(likesRepository.findAllByPost(post).size())
                .todoList(post.getTodoList().stream().map(TodoResponseDto::new).collect(Collectors.toList()))
                .commentList(commentResponseDtoList)
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    @Transactional
    public ResponseEntity<String> createPost(PostRequestDto postRequestDto, MultipartFile multipartFile) throws IOException {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(author);

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .author(author)
                .imageUrl(s3Uploader.upload(multipartFile, "static"))
                .todoList(postRequestDto.getTodoList())
                .member(member)
                .build();
        postRepository.save(post);
        createTodo(post);


        return new ResponseEntity<>("글이 작성되었습니다", HttpStatus.CREATED);
    }

    @Transactional
    protected void createTodo(Post post) {
        for (Todo todo : post.getTodoList()) {
            todoRepository.save(Todo.builder()
                    .content(todo.getContent())
                    .done(todo.isDone())
                    .post(post)
                    .build());
        }
    }

    @Transactional
    public ResponseEntity<String> updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if (!post.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        List<Todo> todos = post.getTodoList();
        for (Todo todo : todos) {
            Long todoId = todo.getId();
            todoRepository.deleteById(todoId);
        }
        for (Todo todo : postRequestDto.getTodoList()) {
            todoRepository.save(Todo.builder()
                    .content(todo.getContent())
                    .done(todo.isDone())
                    .post(post)
                    .build());
        }
        post.updatePost(postRequestDto);
        return new ResponseEntity<>("글이 수정되었습니다", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if (!post.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        postRepository.deleteById(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<String> deleteTodo(Long postId, Long todoId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));
        if (!post.getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        todoRepository.deleteById(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
