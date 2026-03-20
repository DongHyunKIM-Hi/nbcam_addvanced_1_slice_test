package org.example.nbcam_addvanced_1.domain.post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDto {

    private String content;
    private int commentCount;
}
