package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.post.exception.WriterNotFoundException;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthPostService {

    private final SecurityUtil securityUtil;

    private final PostRepository postRepository;

    private final WriterRepository writerRepository;

    public void postingLocal(PostingRequest request) {
        User contextUser = securityUtil.getContextInfo();
        try {
            Writer writer = writerRepository.findByUser(contextUser).orElseThrow(RuntimeException::new);
            postRepository.save(Post.builder()
                    .postType(PostType.LOCAL)
                    .region(contextUser.getRegion())
                    .title(request.getTitle())
                    .view(null) // default value 0 is automatically insert in query
                    .writer(writer)
                    .content(request.getContent())
                    .image(request.getImage())
                    .build());
        } catch (RuntimeException e) {
            writerRepository.save(Writer.builder()
                            .writerType(WriterType.USER)
                            .ipAddress(null)
                            .user(contextUser)
                            .name(contextUser.getNickname())
                    .build());
            throw WriterNotFoundException.EXCEPTION;
        }
    }

    public PostListResponse getLocalPosting(Pageable pageable) {
        Page<Post> posts = postRepository.findByPostType(PostType.LOCAL, pageable);
        return PostListResponse.builder()
                .totalPage(posts.getTotalPages())
                .posts(posts.stream().map((element)-> PostListResponse.PostResponse.builder()
                        .id(element.getId())

                        .title(element.getTitle())

                        .nickname(element.getWriter().getName())

                        .introduce(element.getContent().substring(0,
                                element.getContent().contains(".")
                                        ? element.getContent().indexOf(".") : element.getContent().length()-1))

                        .photo(element.getImage())
                    .build())
                .collect(Collectors.toList()))
                .build();
    }
}
