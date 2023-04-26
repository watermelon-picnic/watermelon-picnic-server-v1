package com.server.watermelonserverv1.domain.auth.service;

import com.server.watermelonserverv1.domain.auth.domain.Refresh;
import com.server.watermelonserverv1.domain.auth.domain.repository.RefreshRepository;
import com.server.watermelonserverv1.domain.auth.exception.ExistEmailException;
import com.server.watermelonserverv1.domain.auth.exception.PasswordIncorrectException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final SecurityUtil securityUtil;

    private final RefreshRepository refreshRepository;

    public void signUp(SignUpRequest request) {
        User dbInfo = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (dbInfo != null) throw ExistEmailException.EXCEPTION;
        userRepository.save(User.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
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
}
