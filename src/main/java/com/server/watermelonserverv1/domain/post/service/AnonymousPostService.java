package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.comment.domain.Comment;
import com.server.watermelonserverv1.domain.comment.domain.repository.CommentRepository;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.post.exception.PostIdNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterNotFoundException;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnonymousPostService {

    private final PostRepository postRepository;

    private final SecurityUtil securityUtil;

    private final WriterRepository writerRepository;

    private final RegionRepository regionRepository;

    private final CommentRepository commentRepository;

    public PostListResponse getPage(Pageable pageable) {
        Page<Post> posts = postRepository.findByPostType(PostType.SHARE, pageable);
        return PostListResponse.builder()
                .totalPage(posts.getTotalPages())
                .posts(posts.stream().map((element)-> PostListResponse.PostResponse.builder()
                        .id(element.getId())
                        .photo(element.getImage())
                        .introduce(element.getContent().contains(".")
                                ? element.getContent().substring(0, element.getContent().indexOf(".")-1)
                                : element.getContent())
                        .nickname(element.getWriter().getName()) // query*************************
                        .title(element.getTitle())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    public void postingShare(PostingRequest request) {
        Writer writer;
        Region region;
        if (securityUtil.getAuthorities().contains("anonymous")) {
            if(writerRepository.findByIpAddress(securityUtil.getIp()).isEmpty()) {
                writerRepository.save(Writer.builder()
                                .writerType(WriterType.ANONYMOUS)
                                .ipAddress(securityUtil.getIp())
                                .user(null)
                                .name(request.getName())
                        .build());
            }
            String writerName = securityUtil.getIp();
            writer = writerRepository.findByIpAddress(writerName).orElseThrow(()-> WriterNotFoundException.EXCEPTION); // 대충 사용자 등록해주세요 같은 예외? ////////////////
            region = regionRepository.findByRegionName(request.getRegion()).orElseThrow(); // region 없으면 등록해줘 같은 예외 ///////
        } else {
            User contextInfo = securityUtil.getContextInfo();
            writer = writerRepository.findByUser(contextInfo).orElseThrow(()->WriterNotFoundException.EXCEPTION); ////////////////////////////////////////////////////
            region = contextInfo.getRegion();
        }
        postRepository.save(Post.builder()
                    .title(request.getTitle())
                    .image(request.getImage())
                    .content(request.getContent())
                    .writer(writer)
                    .view(null)
                    .postType(PostType.SHARE)
                    .region(region)
                .build());
    }

    public PostingDetailResponse postDetail(Long id) {
        Post post = postRepository.findByIdAndPostType(id, PostType.SHARE)
                .orElseThrow(()-> PostIdNotFoundException.EXCEPTION);
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        List<Comment> comments = commentRepository.findByPost(post);
        return PostingDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .region(post.getRegion().getRegionName()) // query********************
                .photo(post.getImage())
                .name(post.getWriter().getName()) // query************************
                .date(dateFormat.format(Date.from(post.getDate())))
                .comments(comments.stream().map(e->
                        PostingDetailResponse.CommentResponse.builder()
                                .name(e.getWriter().getName()) // query*******************
                                .type(e.getCommentType())
                                .content(e.getContent())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
