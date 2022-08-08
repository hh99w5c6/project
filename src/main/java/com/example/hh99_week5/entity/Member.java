package com.example.hh99_week5.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private final List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<SubComment> subCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Likes> likesList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String nickname, String password, Authority authority){
        this.nickname = nickname;
        this.password = password;
        this.authority = authority;
    }
}
