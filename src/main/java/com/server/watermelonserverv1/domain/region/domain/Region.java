package com.server.watermelonserverv1.domain.region.domain;

import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "region_id"))
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "region_unq",
                columnNames = {"region_name"}
        )
)
@Entity
public class Region extends BasedIdEntity {

    @Column(nullable = false, length = 60, name = "region_name")
    private String regionName;

    @Column(length = 1023)
    private String introduce;


    private String image;

    public Region updateIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public Region updateImage(String image) {
        this.image = image;
        return this;
    }

    public Region(String regionName, String introduce) {
        this.regionName = regionName;
        this.introduce = introduce;
    }
}
