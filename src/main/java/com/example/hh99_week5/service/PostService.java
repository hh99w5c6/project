package com.example.hh99_week5.service;

import com.example.hh99_week5.dto.*;
import com.example.hh99_week5.entity.*;
import com.example.hh99_week5.exception.CustomException;
import com.example.hh99_week5.exception.ErrorCode;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final LikesRepository likesRepository;
    private final S3Uploader s3Uploader;
    private final ImgUrlRepository imgUrlRepository;

    @Transactional(readOnly = true)
    public List<PostsResponseDto> readPosts() { // 전체 게시글 조회
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

    @Transactional(readOnly = true) //선택 게시글 조회
    public PostResponseDto readPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if (post.isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : post.get().getCommentList()) {
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
                .id(post.get().getId())
                .title(post.get().getTitle())
                .author(post.get().getAuthor())
                .imgUrl(post.get().getImageUrl())
                .likesCnt(likesRepository.findAllByPost(post.get()).size())
                .todoList(post.get().getTodoList().stream().map(TodoResponseDto::new).collect(Collectors.toList()))
                .commentList(commentResponseDtoList)
                .createdAt(post.get().getCreatedAt())
                .modifiedAt(post.get().getModifiedAt())
                .build();
    }

    @Transactional // 게시글 작성
    public ResponseEntity<String> createPost(PostRequestDto postRequestDto, MultipartFile multipartFile) throws IOException {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.getMembersByNickname(author);
        String[] str = s3Uploader.upload(multipartFile, "static");

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .author(author)
                .imageUrl(str[1])
                .todoList(postRequestDto.getTodoList())
                .member(member)
                .build();
        postRepository.save(post);

        imgUrlRepository.save(ImgUrl.builder()
                .fileName(str[0])
                .url(str[1])
                .post(post)
                .build());

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

    @Transactional // 게시글 수정
    public ResponseEntity<String> updatePost(Long postId, PostRequestDto postRequestDto) {
        Optional<Post> post = postRepository.findById(postId);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if (post.isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        if (!post.get().getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 수정할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        List<Todo> todos = post.get().getTodoList();
        for (Todo todo : todos) {
            Long todoId = todo.getId();
            todoRepository.deleteById(todoId);
        }
        for (Todo todo : postRequestDto.getTodoList()) {
            todoRepository.save(Todo.builder()
                    .content(todo.getContent())
                    .done(todo.isDone())
                    .post(post.get())
                    .build());
        }
        post.get().updatePost(postRequestDto);
        return new ResponseEntity<>("글이 수정되었습니다", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if (post.isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        if (!post.get().getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        postRepository.deleteById(postId);
        return new ResponseEntity<>("게시글 삭제가 완료되었습니다.",HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteTodo(Long postId, Long todoId) {
        Optional<Post> post = postRepository.findById(postId);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다"));
        if (post.isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        if (!post.get().getAuthor().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다", HttpStatus.UNAUTHORIZED);
        }
        todoRepository.deleteById(todoId);
        return new ResponseEntity<>("삭제가 완료되었습니다.",HttpStatus.OK);
    }
}
