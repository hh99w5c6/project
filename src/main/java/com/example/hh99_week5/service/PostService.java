package com.example.hh99_week5.service;

import com.example.hh99_week5.dto.PostRequestDto;
import com.example.hh99_week5.dto.PostResponseDto;
import com.example.hh99_week5.dto.PostsResponseDto;
import com.example.hh99_week5.entity.Post;
import com.example.hh99_week5.entity.Todo;
import com.example.hh99_week5.repository.PostRepository;
import com.example.hh99_week5.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<PostsResponseDto> readPosts(){
        List<Post> postlist = postRepository.findAll();
        List<PostsResponseDto> postsResponseDtoList = new ArrayList<>();
        for(Post post : postlist){
            postsResponseDtoList.add(new PostsResponseDto(post));
        }
        return postsResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto readPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        return new PostResponseDto(post);
    }

    @Transactional
    public void createPost(PostRequestDto postRequestDto) {
        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .author(postRequestDto.getAuthor())
                .todoList(postRequestDto.getTodoList())
                .build();
        postRepository.save(post);
        createTodo(post);
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
}
