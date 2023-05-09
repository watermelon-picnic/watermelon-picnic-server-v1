package com.server.watermelonserverv1.domain.user.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PasswordUpdateRequest {

    @Size(min = 8, max = 20, message = "please follow password size(8~20) requirement")
    @Pattern(
            regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[!#$%&'()*+,./:;<=>?@＼^_`{|}~])[a-zA-Z0-9!#$%&'()*+,./:;<=>?@＼^_`{|}~]{8,20}$",
            message = "should include small letter, number, special in password"
    )
    @NotBlank(message = "please insert any value in password request")
    private String password;

    @NotBlank(message = "please insert any value in passwordToken request")
    private String passwordToken;
}
