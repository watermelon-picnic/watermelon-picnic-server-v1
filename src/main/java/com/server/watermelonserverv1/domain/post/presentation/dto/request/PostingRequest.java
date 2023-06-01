package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class PostingRequest {

    @Size(max = 100, message = "please follow recruitment : maximum title size 100")
    @NotEmpty(message = "please insert any value in title")
    private String title;

    @Size(max = 1000, message = "please follow recruitment : maximum content size 1000")
    @NotEmpty(message = "please insert any value in content")
    private String content;

    // anonymous writer only
    private String region;

    private String name;

    private String password;
}
