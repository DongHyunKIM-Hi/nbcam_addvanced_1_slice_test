package org.example.nbcam_addvanced_1.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostDto;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostSummaryDto;
import org.example.nbcam_addvanced_1.domain.post.service.PostService;
import org.example.nbcam_addvanced_1.common.filter.JwtFilter;
import org.example.nbcam_addvanced_1.common.interceptor.UserOwnerCheckInterceptor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.BDDMockito.given;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerSliceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PostService postService;

    @MockitoBean
    JwtFilter jwtFilter;

    @MockitoBean
    UserOwnerCheckInterceptor userOwnerCheckInterceptor;

    @Test
    @WithMockUser                       // ① Security 필터를 통과시키기 위한 가짜 인증 사용자
    void postId로_게시글_조회시_200_응답() throws Exception {

        // given ── Mock 동작 설정
        PostDto postDto = new PostDto(1L, "테스트 게시글 내용", "user1");
        given(postService.getPost(1L))  // ② postService.getPost(1L)이 호출되면
            .willReturn(postDto);       // ③ 이 postDto를 반환해라

        // when & then ── 요청 전송 + 응답 검증
        mockMvc.perform(get("/api/post/1"))             // ④ GET /api/post/1 요청
            .andExpect(status().isOk())              // ⑤ 상태코드 200인지 확인
            .andExpect(jsonPath("$.id").value(1L))   // ⑥ 응답 JSON의 id 필드가 1인지 확인
            .andExpect(jsonPath("$.content").value("테스트 게시글 내용"))
            .andExpect(jsonPath("$.username").value("user1"));
    }
    @Test
    @WithMockUser
    void username으로_게시글_목록_조회시_200_응답() throws Exception {

        // given
        List<PostDto> postList = List.of(
            new PostDto(1L, "첫 번째 게시글", "user1"),
            new PostDto(2L, "두 번째 게시글", "user1")
        );
        given(postService.getPostListByUsername("user1"))
            .willReturn(postList);

        // when & then
        mockMvc.perform(get("/api/post/user/user1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))              // ① 리스트 크기 확인
            .andExpect(jsonPath("$[0].id").value(1L))                // ② 첫 번째 요소 id 확인
            .andExpect(jsonPath("$[0].content").value("첫 번째 게시글"))
            .andExpect(jsonPath("$[1].content").value("두 번째 게시글")); // ③ 두 번째 요소 확인
    }

    @Test
    @WithMockUser
    void username으로_게시글_요약_목록_조회시_200_응답() throws Exception {

        // given
        List<PostSummaryDto> summaryList = List.of(
            new PostSummaryDto("첫 번",1 ),    // PostSummaryDto는 요약 내용만 포함
            new PostSummaryDto("두 번", 2 )
        );
        given(postService.getPostSummaryListByUsername("user1"))
            .willReturn(summaryList);

        // when & then
        mockMvc.perform(get("/api/post/user/user1/detail"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].content").value("첫 번"))
            .andExpect(jsonPath("$[0].commentCount").value(1))    // ① DTO 필드명에 맞게 작성
            .andExpect(jsonPath("$[1].content").value("두 번"))
            .andExpect(jsonPath("$[1].commentCount").value(2));
    }
}