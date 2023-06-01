package com.server.watermelonserverv1.domain.region.presentation;

import com.server.watermelonserverv1.domain.region.presentation.dto.request.RegionIntroduceRequest;
import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionResponse;
import com.server.watermelonserverv1.domain.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/region")
@RestController
public class RegionController {

    private final RegionService regionService;

    @PutMapping
    public RegionResponse registerRegion(@RequestParam(name = "region_name") String name) { return regionService.registerRegion(name); }

    @PostMapping("/setting/{region}")
    public void registerRegionIntroduce(@PathVariable String region, @RequestBody RegionIntroduceRequest req) { regionService.registerRegionIntroduce(region, req.getIntroduce()); }
}
