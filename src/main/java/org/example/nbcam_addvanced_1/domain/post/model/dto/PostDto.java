package org.example.nbcam_addvanced_1.domain.post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.nbcam_addvanced_1.common.entity.Post;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String content;
    private String username;


    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getContent(), post.getUser().getUsername());
    }
}
