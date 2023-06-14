package com.server.watermelonserverv1.domain.post.presentation;

import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingUpdateRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.post.service.AnonymousPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/post/anonymous")
@RestController
public class AnonymousPostController {

    private final AnonymousPostService anonymousPostService;

    // GET
    @GetMapping
    public PostListResponse getPage(@PageableDefault Pageable pageable) { return anonymousPostService.getPage(pageable); }

    @GetMapping("/region")
    public PostListResponse getRegion(@PageableDefault Pageable pageable, @RequestParam("name") String region) { return anonymousPostService.getRegion(pageable, region); }

    @GetMapping("/{id}")
    public PostingDetailResponse postDetail(@PathVariable Long id) { return anonymousPostService.postDetail(id); }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posting")
    public void postingShare(@RequestPart(value = "file", required = false) MultipartFile file, @Valid @RequestPart("json-body") PostingRequest request) { anonymousPostService.postingShare(file, request); }

    // PUT
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updatePost(@RequestPart(value = "file", required = false) MultipartFile file,
                           @Valid @RequestPart("json-body") PostingUpdateRequest request,
                           @PathVariable Long id) {

        anonymousPostService.updatePost(file, request, id);
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @RequestParam(value = "password", required = false) String password) { anonymousPostService.deletePost(password, id); }
}
