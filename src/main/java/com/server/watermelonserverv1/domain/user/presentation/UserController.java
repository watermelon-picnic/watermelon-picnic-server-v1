package com.server.watermelonserverv1.domain.user.presentation;

import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.user.presentation.dto.request.PasswordUpdateRequest;
import com.server.watermelonserverv1.domain.user.presentation.dto.response.MyInfoResponse;
import com.server.watermelonserverv1.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/reissue")
    public TokenResponse reissue(@RequestHeader(name = "Refresh") String refresh) { return userService.reissue(refresh); }

    @GetMapping("/mail/password")
    public void sendToChangePassword() { userService.sendToChangePassword(); }

    @GetMapping("/password")
    public String passwordSwitchPage() { return userService.passwordSwitchPage(); }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/password")
    public void passwordSwitch(@RequestBody PasswordUpdateRequest request) { userService.passwordSwitch(request.getPasswordToken(), request.getPassword()); }

    @GetMapping("/my-page")
    public MyInfoResponse myPage() { return userService.myPage(); }
}
