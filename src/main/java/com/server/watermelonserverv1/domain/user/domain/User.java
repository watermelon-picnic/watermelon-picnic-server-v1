package com.server.watermelonserverv1.domain.user.domain;

import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.user.domain.type.Role;
import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "user_unq",
                columnNames = {"email", "nickname"}
        )
)
public class User extends BasedIdEntity {

    @Column(nullable = false, length = 35, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @ColumnDefault("'USER'")
    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birth;

    @Setter
    @ManyToOne(targetEntity = Region.class)
    @JoinColumn(name = "region_id")
    private Region region;

    public User updatePassword(String password) {
        this.password = password;
        return this;
    }

    @Builder
    public User(String email, String password, String nickname, Role role, Date birth) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.birth = birth;
    }
}
