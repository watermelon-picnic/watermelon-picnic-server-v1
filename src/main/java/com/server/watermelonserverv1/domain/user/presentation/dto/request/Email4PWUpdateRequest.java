package com.server.watermelonserverv1.domain.user.presentation.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class Email4PWUpdateRequest {

    @Pattern(
            regexp = "[\\d\\w]+@[\\w]+\\.[\\w]+(.[\\w]+)?",
            message = "please follow the requirement common email structure"
    )
    @NotEmpty
    private String email;
}
