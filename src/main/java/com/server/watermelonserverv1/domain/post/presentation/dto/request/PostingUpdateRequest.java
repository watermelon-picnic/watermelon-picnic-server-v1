package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class PostingUpdateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String image;

    private String password;
}
