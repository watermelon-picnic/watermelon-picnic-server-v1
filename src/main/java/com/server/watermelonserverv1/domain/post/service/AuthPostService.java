package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthPostService {

    private final SecurityUtil securityUtil;

    private final PostRepository postRepository;

    public void postingLocal(PostingRequest request) {
        User contextUser = securityUtil.getContextInfo();
        // writer...
        postRepository.save(Post.builder()
                        .postType(request.getType().equals("LOCAL") ? PostType.LOCAL : PostType.SHARE)
                        .region(contextUser.getRegion())
                        .title(request.getTitle())
                        .view(0L)
                        .writer(new Writer("", contextUser, WriterType.USER))
                .build());
    }
}
