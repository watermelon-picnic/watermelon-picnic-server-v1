package com.server.watermelonserverv1.domain.auth.presentation;

import com.server.watermelonserverv1.domain.auth.presentation.dto.request.SignUpRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public TokenResponse login(@Valid @RequestBody SignUpRequest request) { return authService.login(request); }

    @DeleteMapping("/logout")
    public void logout() { authService.logout(); }

    // this api will erase
    @GetMapping("/verification-email")
    public ResponseEntity<String> validationEmail(@RequestHeader String email) { return authService.validationEmail(email); }

    @PutMapping("/reissue")
    public TokenResponse reissue() { return authService.reissue(); }
}
