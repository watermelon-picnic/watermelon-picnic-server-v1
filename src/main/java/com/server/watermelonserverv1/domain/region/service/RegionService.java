package com.server.watermelonserverv1.domain.region.service;

import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.region.exception.RegionAlreadyExistException;
import com.server.watermelonserverv1.domain.region.exception.RegionNotFoundException;
import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionResponse registerRegion(String name) {
        if (regionRepository.findByRegionName(name).isPresent()) throw RegionAlreadyExistException.EXCEPTION;
        Region region = regionRepository.save(new Region(name, null));
        return RegionResponse.fromRegion(region);
    }

    // for admin access a
    public void registerRegionIntroduce(String regionName, String introduce) {
        Region region = regionRepository.findByRegionName(regionName).orElseThrow(()-> RegionNotFoundException.EXCEPTION);
        regionRepository.save(region.updateIntroduce(introduce));
    }
}
