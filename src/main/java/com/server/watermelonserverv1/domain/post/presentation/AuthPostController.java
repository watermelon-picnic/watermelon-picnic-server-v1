package com.server.watermelonserverv1.domain.post.presentation;

import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.service.AuthPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/post/auth")
@RestController
public class AuthPostController {

    private final AuthPostService authPostService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posting")
    public void postLocal(@RequestBody PostingRequest request) {
        authPostService.postingLocal(request);
    }
}
