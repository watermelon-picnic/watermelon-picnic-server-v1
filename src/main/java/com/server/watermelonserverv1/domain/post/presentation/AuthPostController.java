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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // GET
    @GetMapping
    public PostListResponse getLocalPosting(@PageableDefault Pageable pageable) { return authPostService.getLocalPosting(pageable); }

    @GetMapping("/region")
    public PostListResponse getRegion(@PageableDefault Pageable pageable, @RequestParam("name") String region) { return authPostService.getRegion(pageable, region); }

    @GetMapping("/{id}")
    public PostingDetailResponse postDetail(@PathVariable Long id) { return authPostService.postDetail(id); }

    // POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posting")
    public void postLocal(@RequestPart("file") MultipartFile file, @RequestPart("json-body") @Valid PostingRequest request) { authPostService.postingLocal(file, request); }

    // PUT
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updatePosting(@PathVariable Long id,
                              @Valid @RequestPart("json-body") PostingUpdateRequest request,
                              @RequestPart(value = "file", required = false) MultipartFile file) {

        authPostService.updatePost(request, id, file);
    }

    // DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) { authPostService.deletePost(id); }
}
