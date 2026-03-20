package org.example.nbcam_addvanced_1.domain.post.repository;

import java.util.List;
import org.example.nbcam_addvanced_1.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser_Username(String username);

    @Query("SELECT p FROM Post p JOIN FETCH p.comments WHERE p.user.username = :username")
    List<Post> findAllWithCommentsByUsername(String username);
}
