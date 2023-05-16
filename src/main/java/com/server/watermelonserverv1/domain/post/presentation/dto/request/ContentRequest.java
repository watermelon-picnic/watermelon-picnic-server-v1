package com.server.watermelonserverv1.domain.post.presentation.dto.request;

import com.server.watermelonserverv1.domain.content.domain.type.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentRequest {

    private ContentType contentType;

    private String content;
}
