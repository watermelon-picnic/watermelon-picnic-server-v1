package com.server.watermelonserverv1.domain.auth.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class LoginRequest {

    @Size(min = 8, max = 35, message = "please follow email size(8~35) requirement")
    @NotBlank(message = "please insert any value in email request")
    @Pattern(
            regexp = "[0-9a-zA-Z]+@[a-z]+\\.[a-z]+(.[a-z]+)?",
            message = "email is not matched"
    )
    private String email;

    @NotBlank(message = "please insert any value in password request")
    @Size(min = 8, max = 20, message = "please follow password size(8~20) requirement")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[!#$%&'()*+,./:;<=>?@＼^_`{|}~])[a-zA-Z0-9!#$%&'()*+,./:;" +
            "<=>?@＼^_`{|}~]{8,20}$",
            message = "should include small letter, number, special in password")
    private String password;
}
