package com.server.watermelonserverv1.domain.post.presentation;

import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.post.service.AuthPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/post/auth")
@RestController
public class AuthPostController {

    private final AuthPostService authPostService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posting")
    public void postLocal(@RequestBody @Valid PostingRequest request) { authPostService.postingLocal(request); }

    @GetMapping
    public PostListResponse getLocalPosting(@PageableDefault(size = 6) Pageable pageable) { return authPostService.getLocalPosting(pageable); }

    @GetMapping("/{id}")
    public PostingDetailResponse postDetail(@PathVariable Long id) { return authPostService.postDetail(id); }
}
