package com.server.watermelonserverv1.domain.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash
public class Refresh {

    @Id
    private Long id;

    @Indexed
    private String token;

    @TimeToLive
    private Long timeToLive;

    @Builder
    public Refresh(Long id, String token, Long timeToLive) {
        this.id = id;
        this.token = token;
        this.timeToLive = timeToLive;
    }
}
