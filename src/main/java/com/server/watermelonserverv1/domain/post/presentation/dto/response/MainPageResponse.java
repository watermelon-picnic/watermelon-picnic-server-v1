package com.server.watermelonserverv1.domain.post.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MainPageResponse {

    private List<RegionResponse> banners;

    private List<TravelLoad> watermelonLoad;

    private List<NearPost> variableTravelRegion;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class RegionResponse {

        private String image;

        private String introduce;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class TravelLoad {

        private String title;

        private String regionName;

        private String introduce;

        private String writerName;

        private String image;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class NearPost {

        private String image;

        private String title;

        private String content;

    }
}
