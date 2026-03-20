package org.example.nbcam_addvanced_1.domain.post.service;

import org.example.nbcam_addvanced_1.common.entity.Post;
import org.example.nbcam_addvanced_1.common.entity.User;
import org.example.nbcam_addvanced_1.common.enums.UserRoleEnum;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostDto;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostSummaryDto;
import org.example.nbcam_addvanced_1.domain.post.repository.PostRepository;
import org.example.nbcam_addvanced_1.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User testUser;
    private Post testPost;

    @BeforeEach
    void setup() {
        testUser = new User("ravi", "1234", "ravi@example.com", 25, UserRoleEnum.ADMIN);
        testPost = new Post("테스트 게시글입니다.", testUser);
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("게시글 생성 성공 - 유효한 사용자와 내용이 주어졌을 때")
    void createPost_성공() {
        // Given
        when(userRepository.findByUsername("ravi")).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // When
        PostDto result = postService.createPost("ravi", "테스트 게시글입니다.");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("테스트 게시글입니다.");
        assertThat(result.getUsername()).isEqualTo("ravi");

        verify(postRepository, times(1)).save(any(Post.class));
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("게시글 생성 실패 - 등록되지 않은 사용자일 경우 예외 발생")
    void createPost_등록되지_않은_사용자_예외() {
        // Given
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.createPost("unknown", "내용"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("등록된 사용자가 없습니다.");
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("게시글 단건 조회 성공 - ID로 게시글 조회")
    void getPost_성공() {
        // Given
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        // When
        PostDto result = postService.getPost(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(testPost.getContent());
        assertThat(result.getUsername()).isEqualTo(testUser.getUsername());
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("게시글 단건 조회 실패 - 존재하지 않는 ID인 경우 예외 발생")
    void getPost_존재하지_않는_ID_예외() {
        // Given
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.getPost(99L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("등록된 Post가 없습니다.");
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("사용자명으로 게시글 목록 조회 성공")
    void getPostListByUsername_성공() {
        // Given
        List<Post> posts = List.of(
            new Post("첫 번째 게시글", testUser),
            new Post("두 번째 게시글", testUser)
        );

        ReflectionTestUtils.setField(posts.get(0),"id", 1L);
        ReflectionTestUtils.setField(posts.get(1),"id", 2L);

        testUser.getPosts().addAll(posts);
        when(userRepository.findByUsername("ravi")).thenReturn(Optional.of(testUser));

        // When
        List<PostDto> result = postService.getPostListByUsername("ravi");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("ravi");
        assertThat(result.get(1).getContent()).isEqualTo("두 번째 게시글");
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("사용자명으로 게시글 목록 조회 실패 - 등록되지 않은 사용자")
    void getPostListByUsername_등록되지_않은_사용자() {
        // Given
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> postService.getPostListByUsername("unknown"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("등록된 사용자가 없습니다.");
    }

    // -------------------------------------------------------------------
    @Test
    @DisplayName("게시글 요약 리스트 조회 - 댓글 수 포함")
    void getPostSummaryListByUsername_성공() {
        // Given
        Post post1 = new Post("게시글1", testUser);
        Post post2 = new Post("게시글2", testUser);

        // Mock 댓글 리스트
        post1.getComments().addAll(List.of(
            new org.example.nbcam_addvanced_1.common.entity.Comment("댓글1", post1),
            new org.example.nbcam_addvanced_1.common.entity.Comment("댓글2", post1)
        ));
        post2.getComments().addAll(List.of(
            new org.example.nbcam_addvanced_1.common.entity.Comment("댓글3", post2)
        ));

        testUser.getPosts().addAll(List.of(post1, post2));

        when(userRepository.findByUsername("ravi")).thenReturn(Optional.of(testUser));

        // When
        List<PostSummaryDto> result = postService.getPostSummaryListByUsername("ravi");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCommentCount()).isEqualTo(2);
        assertThat(result.get(1).getCommentCount()).isEqualTo(1);
    }
}
