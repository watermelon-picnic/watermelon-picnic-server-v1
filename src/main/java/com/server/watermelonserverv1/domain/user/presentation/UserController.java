package com.server.watermelonserverv1.domain.user.presentation;

import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.user.presentation.dto.response.MyInfoResponse;
import com.server.watermelonserverv1.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PutMapping("/reissue")
    public TokenResponse reissue() { return userService.reissue(); }

    @GetMapping("/mail/password")
    public void sendToChangePassword() { userService.sendToChangePassword(); }

    @GetMapping("/password")
    public String passwordSwitchPage() { return userService.passwordSwitchPage(); }

    @PostMapping("/password")
    public void passwordSwitch(@RequestHeader String passwordToken, @RequestBody String password) { userService.passwordSwitch(passwordToken, password); }

    @GetMapping("/my-page")
    public MyInfoResponse myPage() { return userService.myPage(); }
}
