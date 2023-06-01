package com.server.watermelonserverv1.domain.region.service;

import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.region.exception.RegionNotFoundException;
import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionResponse registerRegion(String name) {
        Region region = regionRepository.findByRegionName(name).orElse(null);
        if (region == null) region = regionRepository.save(new Region(name, null)); //
        return RegionResponse.fromRegion(region);
    }

    // for admin access a
    public void registerRegionIntroduce(String regionName, String introduce) {
        Region region = regionRepository.findByRegionName(regionName).orElseThrow(()-> RegionNotFoundException.EXCEPTION);
        regionRepository.save(region.updateIntroduce(introduce));
    }
}
