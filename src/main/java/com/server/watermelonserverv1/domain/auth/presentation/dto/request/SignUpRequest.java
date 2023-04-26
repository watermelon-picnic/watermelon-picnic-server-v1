package com.server.watermelonserverv1.domain.auth.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class SignUpRequest {

    @Size(min = 0, max = 35)
    private String email;

    @Size(min = 8, max = 255)
    private String password;
    
}
