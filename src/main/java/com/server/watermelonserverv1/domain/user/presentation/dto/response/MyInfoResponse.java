package com.server.watermelonserverv1.domain.user.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MyInfoResponse {

    private final String nickname;

    private final String email;

    private final String region;

    private final String birth;
}
