package com.server.watermelonserverv1.domain.region.domain;

import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Region extends BasedIdEntity {

    @Column(nullable = false, length = 60)
    private String regionName;


    public Region(String regionName) {
        this.regionName = regionName;
    }
}
