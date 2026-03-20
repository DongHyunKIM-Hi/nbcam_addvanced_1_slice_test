package org.example.nbcam_addvanced_1.domain.post.service;


import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.util.List;
import org.example.nbcam_addvanced_1.common.entity.Post;
import org.example.nbcam_addvanced_1.common.entity.User;
import org.example.nbcam_addvanced_1.common.enums.UserRoleEnum;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostDto;
import org.example.nbcam_addvanced_1.domain.post.repository.PostRepository;
import org.example.nbcam_addvanced_1.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 생성 통합 테스트 - 실제 DB에 저장 및 조회 검증")
    void createPost_통합테스트() {
        // Given
        User user = new User("ravi", "1234", "ravi@example.com", 25, UserRoleEnum.ADMIN);
        userRepository.save(user);

        // When
        PostDto result = postService.createPost("ravi", "통합 테스트 게시글입니다.");

        // Then
        assertThat(result.getContent()).isEqualTo("통합 테스트 게시글입니다.");

        // 실제 DB에 저장되었는지 확인
        List<Post> savedPosts = postRepository.findAll();
        assertThat(savedPosts).hasSize(1);
        assertThat(savedPosts.get(0).getUser().getUsername()).isEqualTo("ravi");
    }
}
