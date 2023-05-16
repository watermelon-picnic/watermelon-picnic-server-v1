package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class PostingRequest {

    private String title;

    private List<ContentRequest> contents;
}
