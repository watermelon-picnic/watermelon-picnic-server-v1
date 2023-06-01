package com.server.watermelonserverv1.domain.post.presentation;

import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingUpdateRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.post.service.AuthPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/post/auth")
@RestController
public class AuthPostController {

    private final AuthPostService authPostService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posting")
    public void postLocal(@RequestPart("file") MultipartFile file, @RequestPart("json-body") @Valid PostingRequest request) { authPostService.postingLocal(file, request); }

    @GetMapping
    public PostListResponse getLocalPosting(@PageableDefault(size = 6) Pageable pageable) { return authPostService.getLocalPosting(pageable); }

    // region 이름 별로 로컬 게시글 목록 반환 api

    @GetMapping("/{id}")
    public PostingDetailResponse postDetail(@PathVariable Long id) { return authPostService.postDetail(id); }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updatePosting(@PathVariable Long id, @Valid @RequestBody PostingUpdateRequest request) { authPostService.updatePost(request, id); }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) { authPostService.deletePost(id); }
}
