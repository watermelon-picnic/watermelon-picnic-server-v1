package com.server.watermelonserverv1.domain.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash
public class AuthCode {

    @Id
    private String email;

    @Setter
    @Indexed
    private String authCode;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long timeToLive;

    @Builder
    public AuthCode(String email, String authCode, Long timeToLive) {
        this.email = email;
        this.authCode = authCode;
        this.timeToLive = timeToLive;
    }
}
