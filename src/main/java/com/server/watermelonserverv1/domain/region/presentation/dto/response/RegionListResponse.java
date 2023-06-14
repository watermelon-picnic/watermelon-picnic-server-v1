package com.server.watermelonserverv1.domain.region.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegionListResponse {

    private List<String> regions;
}
