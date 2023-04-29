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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash
public class AuthCode {

    @Id
    private Long id;

    @Setter
    @Indexed
    private String authCode;

    @TimeToLive
    private Long timeToLive;

    @Builder
    public AuthCode(Long id, String authCode, Long timeToLive) {
        this.id = id;
        this.authCode = authCode;
        this.timeToLive = timeToLive;
    }
}
