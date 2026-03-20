package org.example.nbcam_addvanced_1.domain.comment.repository;

import org.example.nbcam_addvanced_1.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
