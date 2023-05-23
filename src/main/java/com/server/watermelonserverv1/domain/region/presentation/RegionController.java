package com.server.watermelonserverv1.domain.region.presentation;

import com.server.watermelonserverv1.domain.region.presentation.dto.response.RegionResponse;
import com.server.watermelonserverv1.domain.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
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
}
