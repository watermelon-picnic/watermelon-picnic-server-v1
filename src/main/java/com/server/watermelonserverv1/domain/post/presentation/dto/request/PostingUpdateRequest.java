package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class PostingUpdateRequest {

    @NotEmpty(message = "please init any value in title")
    private String title;

    @NotEmpty(message = "please init any value in content")
    private String content;

    private String password;
}
