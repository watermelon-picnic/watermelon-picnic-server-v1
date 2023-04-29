package com.server.watermelonserverv1.domain.auth.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignUpRequest {

    @Size(min = 8, max = 35, message = "please follow email size(8~35) requirement")
    @NotBlank(message = "please insert any value in email request")
    @Pattern(
            regexp = "[0-9a-zA-Z]+@[a-z]+\\.[a-z]+(.[a-z]+)?",
            message = "email is not matched"
    )
    private String email;

    @NotBlank(message = "please insert any value in password request")
    @Size(min = 8, max = 255, message = "please follow password size(8~255) requirement")
    private String password;
}
