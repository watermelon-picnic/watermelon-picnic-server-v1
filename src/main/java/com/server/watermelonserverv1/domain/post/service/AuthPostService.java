package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.content.domain.Content;
import com.server.watermelonserverv1.domain.content.domain.repository.ContentRepository;
import com.server.watermelonserverv1.domain.content.domain.type.ContentType;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.post.exception.WriterNotFoundException;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthPostService {

    private final SecurityUtil securityUtil;

    private final PostRepository postRepository;

    private final WriterRepository writerRepository;

    private final ContentRepository contentRepository;

    public void postingLocal(PostingRequest request) {
        User contextUser = securityUtil.getContextInfo();
        try {
            Writer writer = writerRepository.findByUser(contextUser).orElseThrow(RuntimeException::new);
            Post savedPost = postRepository.save(Post.builder()
                    .postType(PostType.LOCAL)
                    .region(contextUser.getRegion())
                    .title(request.getTitle())
                    .view(null)
                    .writer(writer)
                    .build());
            contentRepository.saveAll(request.getContents().stream().map((element)-> Content.builder()
                    .type(element.getContentType())
                    .post(savedPost)
                    .content(element.getContent())
                    .build())
                .collect(Collectors.toList()));
        } catch (RuntimeException e) {
            writerRepository.save(Writer.builder()
                            .writerType(WriterType.USER)
                            .ipAddress(null)
                            .user(contextUser)
                    .build());
            throw WriterNotFoundException.EXCEPTION;
        }
    }
}
