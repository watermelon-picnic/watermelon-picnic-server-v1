package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class PostingRequest {

    private String title;

    private String type;

    private List<Content> contents;
}

@Getter
@AllArgsConstructor
class Content {

    private String contentType;

    private String content;
}
