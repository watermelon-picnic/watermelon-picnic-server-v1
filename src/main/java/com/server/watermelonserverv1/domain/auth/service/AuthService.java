package com.server.watermelonserverv1.domain.auth.service;

import com.server.watermelonserverv1.domain.auth.domain.AuthCode;
import com.server.watermelonserverv1.domain.auth.domain.Refresh;
import com.server.watermelonserverv1.domain.auth.domain.repository.AuthCodeRepository;
import com.server.watermelonserverv1.domain.auth.domain.repository.RefreshRepository;
import com.server.watermelonserverv1.domain.auth.exception.BirthBadRequestException;
import com.server.watermelonserverv1.domain.auth.exception.EmailBadRequestException;
import com.server.watermelonserverv1.domain.auth.exception.ExistEmailException;
import com.server.watermelonserverv1.domain.auth.exception.ExistNicknameException;
import com.server.watermelonserverv1.domain.auth.exception.MessageConfigException;
import com.server.watermelonserverv1.domain.auth.exception.NickNameBadRequestException;
import com.server.watermelonserverv1.domain.auth.exception.PasswordIncorrectException;
import com.server.watermelonserverv1.domain.auth.presentation.dto.request.LoginRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.request.SignUpRequest;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.user.domain.repository.UserRepository;
import com.server.watermelonserverv1.domain.user.domain.type.Role;
import com.server.watermelonserverv1.domain.writer.domain.Writer;
import com.server.watermelonserverv1.domain.writer.domain.repository.WriterRepository;
import com.server.watermelonserverv1.domain.writer.domain.type.WriterType;
import com.server.watermelonserverv1.global.exception.TokenNotFoundException;
import com.server.watermelonserverv1.global.exception.UserNotFoundException;
import com.server.watermelonserverv1.global.security.JwtProvider;
import com.server.watermelonserverv1.global.utils.ResponseUtil;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final ResponseUtil responseUtil;

    private final AuthCodeRepository authCodeRepository;

    private final WriterRepository writerRepository;

    public boolean isNicknameExist(String nickname) {
        if (nickname == null || nickname.isEmpty()) throw NickNameBadRequestException.EXCEPTION;
        return userRepository.findByNickname(nickname).isPresent();
    }

    public void signUp(SignUpRequest request) {
        User dbInfo = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (dbInfo != null) throw ExistEmailException.EXCEPTION;
        dbInfo = userRepository.findByNickname(request.getNickname()).orElse(null);
        if (dbInfo != null) throw ExistNicknameException.EXCEPTION;
        DateFormat format = new SimpleDateFormat("yyMMdd");
        try {
            User savedUser = userRepository.save(User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .nickname(request.getNickname())
                    .birth(format.parse(request.getBirth()))
                    .build());
            if (writerRepository.findByUser(savedUser).isEmpty())
                writerRepository.save(Writer.builder()
                                .user(savedUser)
                                .ipAddress(null)
                                .writerType(WriterType.USER)
                                .name(savedUser.getNickname())
                        .build());
        } catch (ParseException e) { throw BirthBadRequestException.EXCEPTION; }
    }

    public TokenResponse login(LoginRequest request) {
        User dbInfo = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->UserNotFoundException.EXCEPTION);
        if (!passwordEncoder.matches(request.getPassword(), dbInfo.getPassword()))
            throw PasswordIncorrectException.EXCEPTION;
        return TokenResponse.builder()
                .accessToken(jwtProvider.accessTokenGenerator(dbInfo.getEmail()))
                .refreshToken(jwtProvider.refreshTokenGenerator(dbInfo))
                .build();
    }

    public String emailSender(String email) {
        if (userRepository.findByEmail(email).isPresent())
            throw ExistEmailException.EXCEPTION;
        Pattern pattern = Pattern.compile("[\\d\\w]+@[\\w]+\\.[\\w]+(.[\\w]+)?");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) throw EmailBadRequestException.EXCEPTION;
        String randomValue = String.format("%d", (int)Math.floor(Math.random() * (999999 - 100000 + 1) + 100000));
        String htmlMsg = String.format("<h1>이메일 인증코드입니다.</h1><br/><strong>%s</strong>", randomValue);
        authCodeRepository.save(AuthCode.builder()
                        .email(email)
                        .authCode(randomValue)
                        .timeToLive(5L)
                .build());
        responseUtil.sendMail(email, htmlMsg);
        return randomValue;
    }

    public void logout() {
        User contextInfo = securityUtil.getContextInfo();
        Refresh refresh = refreshRepository.findById(contextInfo.getId())
                .orElseThrow(()-> TokenNotFoundException.EXCEPTION);
        refreshRepository.delete(refresh);
    }
}
