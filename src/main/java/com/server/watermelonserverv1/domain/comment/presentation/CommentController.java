package com.server.watermelonserverv1.domain.comment.presentation;

import com.server.watermelonserverv1.domain.comment.presentation.dto.request.PostCommentRequest;
import com.server.watermelonserverv1.domain.comment.presentation.dto.request.PutCommentRequest;
import com.server.watermelonserverv1.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}")
    public void postComment(@PathVariable Long id, @RequestBody PostCommentRequest request) { commentService.postComment(id, request); }

    @PutMapping("/{id}")
    public void PutComment(@PathVariable Long id, @RequestBody PutCommentRequest request) { commentService.putComment(id, request); }
}
