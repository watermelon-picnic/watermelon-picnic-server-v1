package com.server.watermelonserverv1.domain.region.service;

import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.region.exception.RegionAlreadyExistException;
import com.server.watermelonserverv1.domain.region.exception.RegionNotFoundException;
import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionListResponse;
import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionListResponse getRegionInfo() {
        Iterable<Region> regions = regionRepository.findAll();
        List<String> response = new ArrayList<>();
        for (Region region : regions) { response.add(region.getRegionName()); }
        return new RegionListResponse(response);
    }

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
