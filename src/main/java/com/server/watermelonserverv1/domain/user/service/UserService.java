package com.server.watermelonserverv1.domain.user.service;

import com.server.watermelonserverv1.domain.auth.domain.repository.RefreshRepository;
import com.server.watermelonserverv1.domain.auth.exception.TokenExpiredException;
import com.server.watermelonserverv1.domain.auth.exception.TokenTypeNotMatchedException;
import com.server.watermelonserverv1.domain.auth.presentation.dto.response.TokenResponse;
import com.server.watermelonserverv1.domain.region.domain.Region;
import com.server.watermelonserverv1.domain.region.domain.repository.RegionRepository;
import com.server.watermelonserverv1.domain.region.exception.RegionNotFoundException;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.domain.user.domain.repository.UserRepository;
import com.server.watermelonserverv1.domain.user.presentation.dto.response.MyInfoResponse;
import com.server.watermelonserverv1.global.exception.TokenNotFoundException;
import com.server.watermelonserverv1.global.exception.UserNotFoundException;
import com.server.watermelonserverv1.global.security.JwtProvider;
import com.server.watermelonserverv1.global.utils.ResponseUtil;
import com.server.watermelonserverv1.global.utils.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Service
public class UserService {

    private final SecurityUtil securityUtil;

    private final RefreshRepository refreshRepository;

    private final JwtProvider jwtProvider;

    private final ResponseUtil responseUtil;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RegionRepository regionRepository;

    // GET
    public String passwordSwitchPage() {
        String email = securityUtil.getContextInfo().getEmail();
        return jwtProvider.passwordTokenGenerator(email);
    }

    public MyInfoResponse myPage() {
        User contextInfo = securityUtil.getContextInfo();
        DateFormat format = new SimpleDateFormat("yyMMdd");
        return MyInfoResponse.builder()
                .email(contextInfo.getEmail())
                .nickname(contextInfo.getNickname())
                .birth(format.format(contextInfo.getBirth()))
                .region((contextInfo.getRegion() != null) ? (contextInfo.getRegion().getRegionName()) :("지역정보가 없습니다."))
                .build();
    }

    // POST
    public void sendToChangePassword(String email) {
        // the exception happen reason is @Async annotation makes new thread, the new thread doesn't save Authentication
//        String contextInfo = securityUtil.getContextInfo().getNickname();
        String htmlMsg = "<h3>비밀번호 변경을 원하신다면 다음 링크를 클릭해 주십시오</h3><br/><a href=\"{URL}\">비밀번호 변경하러 가기</a>";
        responseUtil.sendMail(email, htmlMsg);
    }

    // PUT
    public TokenResponse reissue(String refresh) {
        Claims claims = jwtProvider.parseToken(refresh);
        User contextInfo = userRepository.findByEmail(claims.getSubject())
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);
        String token = refreshRepository.findById(contextInfo.getId())
                .orElseThrow(()-> TokenNotFoundException.EXCEPTION).getToken();
        if (jwtProvider.tokenExpired(token)) throw TokenExpiredException.EXCEPTION;
        return TokenResponse.builder()
                .accessToken(jwtProvider.accessTokenGenerator(contextInfo.getEmail()))
                .refreshToken(jwtProvider.refreshTokenGenerator(contextInfo))
                .build();
    }

    public void passwordSwitch(String token, String password) {
        User contextInfo = securityUtil.getContextInfo();
        Claims claims = jwtProvider.parseToken(token);
        if (!claims.get("type").equals("PASSWORD")) throw TokenTypeNotMatchedException.EXCEPTION;
        userRepository.save(contextInfo.updatePassword(passwordEncoder.encode(password)));
    }

    public void setUserRegionInformation(String regionName) {
        Region region = regionRepository.findByRegionName(regionName).orElseThrow(()-> RegionNotFoundException.EXCEPTION);
        userRepository.save(securityUtil.getContextInfo().setRegion(region));
    }
}
