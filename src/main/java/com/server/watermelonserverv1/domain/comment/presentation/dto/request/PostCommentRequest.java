package com.server.watermelonserverv1.domain.comment.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostCommentRequest {

    @NotBlank
    private String content;

    // for anonymous user

    // for user writing first posting in this service
    private String name;

    // for anonymous user to access this comment
    private String password;
}
