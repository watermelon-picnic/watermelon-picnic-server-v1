package com.server.watermelonserverv1.domain.writer.domain;

import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "writer_id"))
@Entity
public class Writer extends BasedIdEntity {

    @Column(name = "ip_address", length = 35)
    private String ipAddress;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;

    @Builder
    public Writer(String ipAddress, User user) {
        this.ipAddress = ipAddress;
        this.user = user;
    }
}
