package com.server.watermelonserverv1.domain.user.service;

import com.server.watermelonserverv1.domain.auth.domain.repository.RefreshRepository;
import com.server.watermelonserverv1.domain.auth.exception.MessageConfigException;
import com.server.watermelonserverv1.domain.auth.exception.TokenExpiredException;
import com.server.watermelonserverv1.domain.auth.exception.TokenTypeNotMatchedException;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.user.domain.repository.UserRepository;
import com.server.watermelonserverv1.global.exception.TokenNotFoundException;
import com.server.watermelonserverv1.global.security.JwtProvider;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class UserService {

    private final SecurityUtil securityUtil;

    private final RefreshRepository refreshRepository;

    private final JwtProvider jwtProvider;

    private final JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public TokenResponse reissue() {
        User contextInfo = securityUtil.getContextInfo();
        String token = refreshRepository.findById(contextInfo.getId())
                .orElseThrow(()-> TokenNotFoundException.EXCEPTION).getToken();
        if (jwtProvider.tokenExpired(token)) throw TokenExpiredException.EXCEPTION;
        return TokenResponse.builder()
                .accessToken(jwtProvider.accessTokenGenerator(contextInfo.getEmail()))
                .refreshToken(jwtProvider.refreshTokenGenerator(contextInfo))
                .build();
    }

    public void sendToChangePassword() {
        User contextInfo = securityUtil.getContextInfo();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = String.format(
                "<h3>%s 님! 비밀번호 변경을 원하신다면 다음 링크를 클릭해 주십시오</h3><br/><a href=\"{URL}\">비밀번호 변경하러 가기</a>",
                contextInfo.getNickname());
        try {
            helper.setTo(contextInfo.getEmail());
            helper.setSubject("수박나들이에서 보냅니다.");
            helper.setText(htmlMsg, true);
        } catch (MessagingException e) { throw MessageConfigException.EXCEPTION; }
        javaMailSender.send(mimeMessage);
    }

    public String passwordSwitchPage() {
        String email = securityUtil.getContextInfo().getEmail();
        return jwtProvider.passwordTokenGenerator(email);
    }

    public void passwordSwitch(String token, String password) {
        User contextInfo = securityUtil.getContextInfo();
        Claims claims = jwtProvider.parseToken(token);
        if (!claims.get("type").equals("PASSWORD")) throw TokenTypeNotMatchedException.EXCEPTION;
        userRepository.save(contextInfo.updatePassword(passwordEncoder.encode(password)));
    }
}
