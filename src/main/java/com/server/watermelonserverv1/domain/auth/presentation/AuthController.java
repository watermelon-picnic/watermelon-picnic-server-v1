package com.server.watermelonserverv1.domain.auth.presentation;

import com.server.watermelonserverv1.domain.auth.presentation.dto.request.LoginRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.request.SignUpRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpRequest request) { authService.signUp(request); }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) { return authService.login(request); }

    @DeleteMapping("/logout")
    public void logout() { authService.logout(); }

    // this api will erase
    @GetMapping("/verification-email")
    public void validationEmail(@RequestHeader String email) { authService.validationEmail(email); }

    @PutMapping("/reissue")
    public TokenResponse reissue() { return authService.reissue(); }

    @GetMapping("/transmission-email")
    public String sendEmail(@RequestHeader String email) { return authService.emailSender(email); }

    @GetMapping("/nickname/{nickname}")
    public boolean isNicknameExist(@PathVariable String nickname) { return authService.isNicknameExist(nickname); }

    @GetMapping("/mail/password")
    public void sendToChangePassword() { authService.sendToChangePassword(); }

    @GetMapping("/password")
    public String passwordSwitchPage() { return authService.passwordSwitchPage(); }
}
