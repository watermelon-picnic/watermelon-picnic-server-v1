package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class PostingRequest {

    @NotEmpty(message = "please insert any value in title")
    private String title;

    @NotEmpty(message = "please insert any value in content")
    private String content;

    private String image;
}
