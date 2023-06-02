package com.server.watermelonserverv1.domain.auth.presentation;

import com.server.watermelonserverv1.domain.auth.presentation.dto.request.LoginRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.request.SignUpRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpRequest request) { authService.signUp(request); }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) { return authService.login(request); }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public void logout() { authService.logout(); }


    // this api will erase
//    @GetMapping("/email/verification")
//    public void validationEmail(@RequestHeader String email) { authService.validationEmail(email); }

    @PostMapping("/email/transmission")
    public void sendEmail(@RequestHeader String email) { authService.emailSender(email); }

    @GetMapping("/nickname/{nickname}")
    public boolean isNicknameExist(@PathVariable String nickname) { return authService.isNicknameExist(nickname); }
}
