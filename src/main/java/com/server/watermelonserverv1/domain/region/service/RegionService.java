package com.server.watermelonserverv1.domain.region.service;

import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionResponse registerRegion(String name) {
        Region region = regionRepository.findByRegionName(name).orElse(null);
        if (region == null) region = regionRepository.save(new Region(name));
        return RegionResponse.fromRegion(region);
    }
}
