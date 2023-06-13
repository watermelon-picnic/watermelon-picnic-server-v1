package com.server.watermelonserverv1.domain.comment.presentation;

import com.server.watermelonserverv1.domain.comment.presentation.dto.request.PostCommentRequest;
import com.server.watermelonserverv1.domain.comment.presentation.dto.request.PutCommentRequest;
import com.server.watermelonserverv1.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public void postComment(@PathVariable Long id, @Valid @RequestBody PostCommentRequest request) { commentService.postComment(id, request); }

    @PutMapping("/{id}")
    public void putComment(@PathVariable Long id, @Valid @RequestBody PutCommentRequest request) { commentService.putComment(id, request); }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody(required = false) String password) { commentService.deleteComment(id, password); }
}
