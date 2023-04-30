package com.server.watermelonserverv1.domain.auth.service;

import com.server.watermelonserverv1.domain.auth.domain.AuthCode;
import com.server.watermelonserverv1.domain.auth.domain.Refresh;
import com.server.watermelonserverv1.domain.auth.domain.repository.AuthCodeRepository;
import com.server.watermelonserverv1.domain.auth.domain.repository.RefreshRepository;
import com.server.watermelonserverv1.domain.auth.exception.ExistEmailException;
import com.server.watermelonserverv1.domain.auth.exception.PasswordIncorrectException;
import com.server.watermelonserverv1.domain.auth.exception.TokenExpiredException;
import com.server.watermelonserverv1.domain.auth.presentation.dto.request.SignUpRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.user.domain.repository.UserRepository;
import com.server.watermelonserverv1.domain.user.domain.type.Role;
import com.server.watermelonserverv1.global.exception.TokenNotFoundException;
import com.server.watermelonserverv1.global.exception.UserNotFoundException;
import com.server.watermelonserverv1.global.security.JwtProvider;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final SecurityUtil securityUtil;

    private final RefreshRepository refreshRepository;

    private final JavaMailSender javaMailSender;

    private final AuthCodeRepository authCodeRepository;

    public void signUp(SignUpRequest request) {
        User dbInfo = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (dbInfo != null) throw ExistEmailException.EXCEPTION;
        userRepository.save(User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                .build());
    }

    public TokenResponse login(SignUpRequest request) {
        User dbInfo = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->UserNotFoundException.EXCEPTION);
        if (!passwordEncoder.matches(request.getPassword(), dbInfo.getPassword()))
            throw PasswordIncorrectException.EXCEPTION;
        return TokenResponse.builder()
                .accessToken(jwtProvider.accessTokenGenerator(dbInfo.getEmail()))
                .refreshToken(jwtProvider.refreshTokenGenerator(dbInfo))
                .build();
    }

    public void logout() {
        User contextInfo = securityUtil.getContextInfo();
        Refresh refresh = refreshRepository.findById(contextInfo.getId())
                .orElseThrow(()-> TokenNotFoundException.EXCEPTION);
        refreshRepository.delete(refresh);
    }

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

    public ResponseEntity<String> validationEmail(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw ExistEmailException.EXCEPTION;
        Pattern pattern = Pattern.compile("[\\d\\w]+@[\\w]+\\.[\\w]+(.[\\w]+)?");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) return new ResponseEntity<>("verification complete", HttpStatus.OK);
        return new ResponseEntity<>("email is not matched in requirement", HttpStatus.BAD_REQUEST);
    }

    public void emailSender(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String randomValue = String.format("%d", (int)Math.floor(Math.random() * (999999 - 100000 + 1) + 100000));
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("수박나들이에서 보냅니다.");
        simpleMailMessage.setText("이메일 인증코드입니다.\n"+randomValue);
        authCodeRepository.save(AuthCode.builder()
                        .email(email)
                        .authCode(randomValue)
                        .timeToLive(5L)
                .build());
        javaMailSender.send(simpleMailMessage);
    }
}
