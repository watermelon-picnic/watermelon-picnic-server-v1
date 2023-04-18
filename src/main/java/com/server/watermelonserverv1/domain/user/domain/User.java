package com.server.watermelonserverv1.domain.user.domain;

import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.user.domain.type.Role;
import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "user_unq",
                columnNames = {"email", "accountId"}
        )
)
public class User extends BasedIdEntity {

    @Column(nullable = false, length = 35, unique = true)
    private String email;

    @Column(nullable = false, length = 20, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @ColumnDefault("USER")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(targetEntity = Region.class)
    @JoinColumn(name = "region_id")
    private Region region;
    
    @Builder
    public User(String email, String accountId, String password) {
        this.email = email;
        this.accountId = accountId;
        this.password = password;
    }
}
