package com.server.watermelonserverv1.domain.post.service;

import com.server.watermelonserverv1.domain.post.domain.Post;
import com.server.watermelonserverv1.domain.post.domain.repository.PostRepository;
import com.server.watermelonserverv1.domain.post.presentation.dto.response.MainPageResponse;
import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MainPageService {

    private final PostRepository postRepository;

    private final RegionRepository regionRepository;

    public MainPageResponse mainPage() {
        final List<Post> posts = postRepository.findByOrderByDateDesc();
        final List<Region> regions = (List<Region>) regionRepository.findAll();
        return MainPageResponse.builder()
                .banners(
                        regions.subList(0, Math.min(regions.size(), 3))
                                .stream().map(
                                e->MainPageResponse.RegionResponse
                                        .builder()
                                        .introduce(e.getIntroduce())
                                        .image(e.getImage())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .watermelonLoad(
                        posts.subList(0, Math.min(posts.size(), 13))
                                .stream().map(
                        e->MainPageResponse.TravelLoad.builder()
                                .title(e.getTitle())
                                .regionName(e.getRegion().getRegionName())
                                .introduce(ResponseUtil.makeIntro(e.getContent()))
                                .writerName(e.getWriter().getName())
                                .image(e.getImage())
                                .type(e.getPostType())
                                .id(e.getId())
                                .build()
                ).collect(Collectors.toList()))
                .variableTravelRegion(
                        regions.subList(0, Math.min(regions.size(), 9))
                                .stream().map(
                        e->MainPageResponse.NearPost.builder()
                                .content(ResponseUtil.makeIntro(e.getIntroduce()))
                                .region(e.getRegionName())
                                .image(e.getImage())
                                .id(e.getId())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
