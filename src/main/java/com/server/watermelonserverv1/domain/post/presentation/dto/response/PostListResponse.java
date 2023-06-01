package com.server.watermelonserverv1.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostListResponse {

    private int totalPage;

    private List<PostResponse> posts;

    private List<String> regions;

    @Getter @AllArgsConstructor @Builder
    public static class PostResponse {
        private Long id;
        private String title;
        private String nickname;
        private String introduce;
        private String photo;
    }
}
