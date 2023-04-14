package com.server.watermelonserverv1.domain.user.domain;

import com.server.watermelonserverv1.global.entity.BasedIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BasedIdEntity {

    @Column(nullable = false, length = 35)
    private String email;

    @Column(nullable = false, length = 20)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(String email, String accountId, String password) {
        this.email = email;
        this.accountId = accountId;
        this.password = password;
    }
}
