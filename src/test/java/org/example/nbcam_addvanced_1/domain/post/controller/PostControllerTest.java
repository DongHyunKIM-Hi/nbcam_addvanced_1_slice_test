package org.example.nbcam_addvanced_1.domain.post.controller;

import org.example.nbcam_addvanced_1.common.entity.User;
import org.example.nbcam_addvanced_1.common.enums.UserRoleEnum;
import org.example.nbcam_addvanced_1.common.utils.JwtUtil;
import org.example.nbcam_addvanced_1.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setup() {
        User user = new User("ravi", "1234", "ravi@example.com", 25, UserRoleEnum.ADMIN);
        userRepository.save(user);
        token = jwtUtil.generateToken(user.getUsername(), user.getRole());
    }

    @Test
    @DisplayName("POST /api/post - 게시글 생성 요청 성공")
    void createPost_요청성공() throws Exception {
        String requestBody = """
            { "username": "ravi", "content": "통합 테스트 게시글" }
        """;

        mockMvc.perform(post("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("통합 테스트 게시글"))
            .andExpect(jsonPath("$.username").value("ravi"));
    }
}