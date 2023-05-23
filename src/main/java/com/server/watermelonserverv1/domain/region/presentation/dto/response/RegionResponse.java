package com.server.watermelonserverv1.domain.region.presentation.dto.response;

import com.server.watermelonserverv1.domain.region.domain.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionResponse {

    private final Long regionId;

    private final String regionName;

    public static RegionResponse fromRegion(Region region) {
        return new RegionResponse(region.getId(), region.getRegionName());
    }
}
