package com.server.watermelonserverv1.domain.comment.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PutCommentRequest {

    @NotBlank(message = "please init any value in content")
    private String content;

    private String password;
}
