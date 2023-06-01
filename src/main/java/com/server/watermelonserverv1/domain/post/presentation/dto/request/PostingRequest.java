package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class PostingRequest {

    @NotEmpty(message = "please insert any value in title")
    private String title;

    @NotEmpty(message = "please insert any value in content")
    private String content;

    // anonymous writer only
    private String region;

    private String name;

    private String password;
}
