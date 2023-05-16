package com.server.watermelonserverv1.domain.post.presentation.dto.response;

import com.server.watermelonserverv1.domain.comment.domain.type.CommentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostingDetailResponse {

    private final String title;

    private final String name;

    private final String region;

    private final String date;

    private final String content;

    private final String photo;

    private List<CommentResponse> comments;

    @Getter @AllArgsConstructor @Builder
    public static class CommentResponse {

        private final String name;

        private final String content;

        private final CommentType type;
    }
}
