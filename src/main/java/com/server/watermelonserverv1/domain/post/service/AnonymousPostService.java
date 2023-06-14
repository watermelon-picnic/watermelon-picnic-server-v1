package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.auth.exception.PasswordIncorrectException;
import com.server.watermelonserverv1.domain.comment.domain.Comment;
import com.server.watermelonserverv1.domain.comment.domain.repository.CommentRepository;
import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.domain.type.PostType;
import com.server.watermelonserverv1.domain.post.exception.NotExistRegionIsUserConflictException;
import com.server.watermelonserverv1.domain.post.exception.PostIdNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterNotFoundException;
import com.server.watermelonserverv1.domain.post.exception.WriterPostIncorrectException;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.request.PostingUpdateRequest;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostListResponse;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.PostingDetailResponse;
import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.region.exception.RegionNotFoundException;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.security.details.Details;
import com.server.watermelonserverv1.global.utils.ResponseUtil;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import com.server.watermelonserverv1.infrastructure.aws.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    private final PasswordEncoder passwordEncoder;

    private final S3Util s3Util;

    // GET
    public PostListResponse getPage(Pageable pageable) {
        Page<Post> posts = postRepository.findByPostType(PostType.SHARE, pageable);
        List<Region> regions = (List<Region>) regionRepository.findAll();
        return PostListResponse.builder()
                .totalPage(posts.getTotalPages())
                .posts(posts.stream().map((element)-> PostListResponse.PostResponse.builder()
                        .id(element.getId())
                        .photo(element.getImage())
                        .introduce(element.getContent())
                        .nickname(element.getWriter().getName()) // query*************************
                        .title(element.getTitle())
                        .build()).collect(Collectors.toList()))
                .regions(regions.stream().map(Region::getRegionName).collect(Collectors.toList()))
                .build();
    }

    public PostListResponse getRegion(Pageable pageable, String regionName) {
        Region region = regionRepository.findByRegionName(regionName).orElseThrow(()->RegionNotFoundException.EXCEPTION);
        List<Region> regions = (List<Region>) regionRepository.findAll();
        Page<Post> posts = postRepository.findByPostTypeAndRegion(PostType.SHARE, region ,pageable);
        return PostListResponse.builder()
                .totalPage(posts.getTotalPages())
                .regions(regions.stream().map(Region::getRegionName).collect(Collectors.toList()))
                .posts(
                        posts.stream().map(
                                e->PostListResponse.PostResponse.builder()
                                        .id(e.getId())
                                        .title(e.getTitle())
                                        .nickname(e.getWriter().getName())
                                        .introduce(e.getContent())
                                        .photo(e.getImage())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }

    public PostingDetailResponse postDetail(Long id) {
        Post post = postRepository.findByIdAndPostType(id, PostType.SHARE)
                .orElseThrow(()-> PostIdNotFoundException.EXCEPTION);
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        List<Comment> comments = commentRepository.findByPost(post);
        postRepository.save(post.addView());
        return PostingDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .region(post.getRegion().getRegionName()) // query********************
                .photo(post.getImage())
                .name(post.getWriter().getName()) // query************************
                .date(dateFormat.format(Date.from(post.getDate())))
                .comments(comments.stream().map(e->
                        PostingDetailResponse.CommentResponse.builder()
                                .id(e.getId())
                                .name(e.getWriter().getName()) // query*******************
                                .content(e.getContent())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    // POST
    public void postingShare(@Nullable MultipartFile file, PostingRequest request) {
        Writer writer;
        Region region;
        String path = file == null ? null : s3Util.uploadImage(file, "image");
        Authentication concurrentThreadAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String password = null;
        if (concurrentThreadAuthentication instanceof AnonymousAuthenticationToken) {
            if(writerRepository.findByIpAddress(securityUtil.getIp()).isEmpty()) {
                writerRepository.save(Writer.builder()
                                .writerType(WriterType.ANONYMOUS)
                                .ipAddress(securityUtil.getIp())
                                .user(null)
                                .name(request.getName())
                        .build());
            }
            String requestIp = securityUtil.getIp();
            writer = writerRepository.findByIpAddress(requestIp).orElseThrow(()-> WriterNotFoundException.EXCEPTION); // 대충 사용자 등록해주세요 같은 예외? ////////////////
            region = regionRepository.findByRegionName(request.getRegion()).orElseThrow(()->RegionNotFoundException.EXCEPTION); // region 없으면 등록해줘 같은 예외 ///////
            password = passwordEncoder.encode(request.getPassword());
        } else {
            User contextInfo = securityUtil.getContextInfo();
            writer = writerRepository.findByUser(contextInfo).orElseThrow(()->WriterNotFoundException.EXCEPTION); ////////////////////////////////////////////////////
            region = contextInfo.getRegion();
            if (region == null) throw NotExistRegionIsUserConflictException.EXCEPTION;
        }
        postRepository.save(Post.builder()
                    .title(request.getTitle())
                    .image(path)
                    .content(request.getContent())
                    .writer(writer)
                    .view(null)
                    .postType(PostType.SHARE)
                    .region(region)
                    .password(password)
                .build());
    }

    // PUT
    public void updatePost(MultipartFile file, PostingUpdateRequest request, Long id) {
        Post post = postRepository.findByIdAndPostType(id, PostType.SHARE).orElseThrow(()->PostIdNotFoundException.EXCEPTION);
        String newPath = post.getImage();
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            if (!passwordEncoder.matches(request.getPassword(), post.getPassword())) throw PasswordIncorrectException.EXCEPTION;
        } else {
            User contextInfo = securityUtil.getContextInfo();
            if (!post.getWriter().getUser().getId().equals(contextInfo.getId())) throw WriterPostIncorrectException.EXCEPTION;
        }
        if (file != null) {
            s3Util.delete(post.getImage());
            newPath = s3Util.uploadImage(file, "image");
        }
        postRepository.save(post.updateInfo(request.getTitle(), request.getContent(), newPath));
    }

    // DELETE
    public void deletePost(@Nullable String password, Long id) {
        Post post = postRepository.findByIdAndPostType(id, PostType.SHARE).orElseThrow(()->PostIdNotFoundException.EXCEPTION);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            if (!passwordEncoder.matches(password, post.getPassword())) throw PasswordIncorrectException.EXCEPTION;
        } else {
            Details details = (Details) authentication.getPrincipal();
            if (!post.getWriter().getUser().getId().equals(details.getUser().getId())) throw WriterPostIncorrectException.EXCEPTION;
        }
        s3Util.delete(post.getImage());
        postRepository.delete(post);
    }
}
