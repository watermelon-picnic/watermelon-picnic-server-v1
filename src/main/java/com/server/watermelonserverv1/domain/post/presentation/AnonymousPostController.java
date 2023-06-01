package com.server.watermelonserverv1.domain.post.presentation;

import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingUpdateRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.post.service.AnonymousPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/post/anonymous")
@RestController
public class AnonymousPostController {

    private final AnonymousPostService anonymousPostService;

    @GetMapping
    public PostListResponse getPage(@PageableDefault Pageable pageable) { return anonymousPostService.getPage(pageable); }

    @PostMapping("/posting")
    public void postingShare(@RequestPart("file") MultipartFile file, @Valid @RequestPart("json-body") PostingRequest request) { anonymousPostService.postingShare(file, request); }

    @GetMapping("/{id}")
    public PostingDetailResponse postDetail(@PathVariable Long id) { return anonymousPostService.postDetail(id); }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody PostingUpdateRequest request, @PathVariable Long id) { anonymousPostService.updatePost(request, id); }

//    @PostMapping("/upload")
//    public String uploadImage(@RequestPart("file") MultipartFile file, @RequestPart("json-body") PostingRequest request) {
//        return anonymousPostService.uploadImage(file, request);
//    }
}
