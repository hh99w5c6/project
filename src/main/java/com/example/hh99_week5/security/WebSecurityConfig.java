package com.example.hh99_week5.security;

import com.example.hh99_week5.jwt.JwtAccessDeniedHandler;
import com.example.hh99_week5.jwt.JwtAuthenticationEntryPoint;
import com.example.hh99_week5.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF protection 을 비활성화
        http.csrf().disable();

        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/api/member/**").permitAll() // 로그인,회원가입은 토큰없이도 가능
                        .antMatchers("/api/posts/**").permitAll() // 전체 게시글 조회하기,선택 게시글 조회하기 로그인 없이도 가능
//                        .antMatchers("/api/post/**").permitAll() // api/post/** 는 모두 게시글 작성,수정,삭제 이므로 로그인이 필요하다.
//                        .antMatchers("/api/comment/**").permitAll() // 댓글,대댓글 작성도 로그인이 반드시 필요하다.
//                        .antMatchers("/api/likes/**").permitAll() // 좋아요는 로그인 필요없음?
                        // 나머지 어떤 요청이든 '인증' 필요
                        .anyRequest().authenticated());

        http.headers()
                .frameOptions()
                .sameOrigin();


        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http.apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
