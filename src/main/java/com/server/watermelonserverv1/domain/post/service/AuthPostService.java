package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.comment.domain.Comment;
import com.server.watermelonserverv1.domain.comment.domain.repository.CommentRepository;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.post.exception.PostIdNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterPostIncorrectException;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingUpdateRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.utils.ResponseUtil;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import com.server.watermelonserverv1.infrastructure.aws.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthPostService {

    private final SecurityUtil securityUtil;

    private final PostRepository postRepository;

    private final WriterRepository writerRepository;

    private final CommentRepository commentRepository;

    private final RegionRepository regionRepository;

    private final S3Util s3Util;

    public void postingLocal(MultipartFile file, PostingRequest request) {
        User contextUser = securityUtil.getContextInfo();
        String path = s3Util.uploadImage(file, "image");
        try {
            Writer writer = writerRepository.findByUser(contextUser).orElseThrow(RuntimeException::new);
            postRepository.save(Post.builder()
                    .postType(PostType.LOCAL)
                    .region(contextUser.getRegion())
                    .title(request.getTitle())
                    .view(null) // default value 0 is automatically insert in query
                    .writer(writer)
                    .content(request.getContent())
                    .image(path)
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
        List<Region> regions = (List<Region>) regionRepository.findAll();
        return PostListResponse.builder()
                .totalPage(posts.getTotalPages())
                .posts(posts.stream().map((element)-> PostListResponse.PostResponse.builder()
                        .id(element.getId())
                        .title(element.getTitle())
                        .nickname(element.getWriter().getName()) // query********************************
                        .introduce(ResponseUtil.makeIntro(element.getContent()))
                        .photo(element.getImage())
                    .build())
                .collect(Collectors.toList()))
                .regions(regions.stream().map(Region::getRegionName).collect(Collectors.toList()))
                .build();
    }

    public PostingDetailResponse postDetail(Long id) {
        Post post = postRepository.findByIdAndPostType(id, PostType.LOCAL)
                .orElseThrow(()-> PostIdNotFoundException.EXCEPTION);
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        List<Comment> comments = commentRepository.findByPost(post);
        return PostingDetailResponse.builder()
                .title(post.getTitle())
                .name(post.getWriter().getName()) // query********************************
                .date(dateFormat.format(Date.from(post.getDate())))
                .content(post.getContent())
                .photo(post.getImage())
                .region(post.getRegion().getRegionName()) // query********************************
                .comments(comments.stream()
                        .map((element)->PostingDetailResponse.CommentResponse.builder()
                                .content(element.getContent())
                                .type(element.getCommentType())
                                .name(element.getWriter().getName()) // query********************************
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public void updatePost(PostingUpdateRequest request, Long id) {
        Post post = postRepository.findByIdAndPostType(id, PostType.LOCAL).orElseThrow(()->PostIdNotFoundException.EXCEPTION);
        User contextInfo = securityUtil.getContextInfo();
        if (!post.getWriter().getUser().getId().equals(contextInfo.getId())) throw WriterPostIncorrectException.EXCEPTION;
        postRepository.save(post.updateInfo(request.getTitle(), request.getContent(), request.getImage()));
    }

    public void deletePost(Long id) {
        User contextInfo = securityUtil.getContextInfo();
        Post post = postRepository.findByIdAndPostType(id, PostType.LOCAL).orElseThrow(()->PostIdNotFoundException.EXCEPTION);
        if (!post.getWriter().getUser().getId().equals(contextInfo.getId())) throw WriterPostIncorrectException.EXCEPTION;
        postRepository.delete(post);
    }
}
