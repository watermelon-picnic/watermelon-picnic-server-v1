package com.server.watermelonserverv1.domain.region.domain.repository;

import com.server.watermelonserverv1.domain.region.domain.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<Region, Long> {
    Optional<Region> findByRegionName(String name);
}
