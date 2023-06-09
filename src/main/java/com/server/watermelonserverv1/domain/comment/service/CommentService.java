package com.server.watermelonserverv1.domain.comment.service;

import com.server.watermelonserverv1.domain.auth.exception.PasswordIncorrectException;
import com.server.watermelonserverv1.domain.comment.domain.Comment;
import com.server.watermelonserverv1.domain.comment.domain.repository.CommentRepository;
import com.server.watermelonserverv1.domain.comment.exception.CommentNotFoundException;
import com.server.watermelonserverv1.domain.comment.presentation.dto.request.PostCommentRequest;
import com.server.watermelonserverv1.domain.comment.presentation.dto.request.PutCommentRequest;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.exception.PostIdNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterPostIncorrectException;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.util.annotation.Nullable;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final SecurityUtil securityUtil;

    private final WriterRepository writerRepository;

    private final PasswordEncoder passwordEncoder;

    public void postComment(Long id, PostCommentRequest request) {
        Post post = postRepository.findById(id).orElseThrow(()-> PostIdNotFoundException.EXCEPTION);
        Writer writer;
        String password = null;
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            writer = writerRepository.findByIpAddress(securityUtil.getIp()).orElse(null);
            password = passwordEncoder.encode(request.getPassword());
            if (writer == null) writer = writerRepository.save(Writer.builder()
                            .user(null)
                            .ipAddress(securityUtil.getIp())
                            .writerType(WriterType.ANONYMOUS)
                            .name(request.getName())
                    .build());
        } else writer = writerRepository.findByUser(securityUtil.getContextInfo()).orElseThrow(()-> WriterNotFoundException.EXCEPTION);
        commentRepository.save(Comment.builder()
                        .content(request.getContent())
                        .post(post)
                        .writer(writer)
                        .password(password)
                .build());
    }

    public void putComment(Long id, PutCommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> CommentNotFoundException.EXCEPTION);
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            if (!passwordEncoder.matches(request.getPassword(), comment.getPassword())) throw PasswordIncorrectException.EXCEPTION;
            commentRepository.save(comment.updateContent(request.getContent()));
        } else {
            Writer writer = writerRepository.findByUser(securityUtil.getContextInfo()).orElseThrow(()->WriterNotFoundException.EXCEPTION);
            if (!writer.getId().equals(comment.getWriter().getId())) throw WriterPostIncorrectException.EXCEPTION;
            commentRepository.save(comment.updateContent(request.getContent()));
        }
    }

    public void deleteComment(Long id, @Nullable String password) {
        Comment deleteComment = commentRepository.findById(id).orElseThrow(()->CommentNotFoundException.EXCEPTION);
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            if (!passwordEncoder.matches(password, deleteComment.getPassword())) throw PasswordIncorrectException.EXCEPTION;
            commentRepository.delete(deleteComment);
        } else {
            Writer writer = writerRepository.findByUser(securityUtil.getContextInfo()).orElseThrow(()->WriterNotFoundException.EXCEPTION);
            if (!writer.getId().equals(deleteComment.getWriter().getId())) throw WriterNotFoundException.EXCEPTION;
            commentRepository.delete(deleteComment);
        }
    }
}
