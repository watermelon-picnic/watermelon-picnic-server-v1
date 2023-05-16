package com.server.watermelonserverv1.global.security;

import com.server.watermelonserverv1.domain.auth.domain.Refresh;
import com.server.watermelonserverv1.domain.auth.domain.repository.AuthCodeRepository;
import com.server.watermelonserverv1.domain.auth.domain.repository.RefreshRepository;
import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.global.security.details.DetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${token.exp.access}")
    private Long accessExp;

    @Value("${token.exp.refresh}")
    private Long refreshExp;

    @Value("${token.secret}")
    private String Secret;

    private final DetailsService detailsService;

//    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Secret));

    private final RefreshRepository refreshRepository;

    private final AuthCodeRepository authCodeRepository;

    private enum TokenType{
        ACCESS, REFRESH, PASSWORD
    }

    public Authentication generateAuthentication(String accountId) {
        UserDetails details = detailsService.loadUserByUsername(accountId);
        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenExpired(String token) {
        Claims tokenClaim = parseToken(token);
        return tokenClaim.getExpiration().before(new Date());
    }

    public String accessTokenGenerator(String email) {
        return generateToken(email, TokenType.ACCESS);
    }

    public String refreshTokenGenerator(User user) {
        String token = generateToken(user.getEmail(), TokenType.REFRESH);
        refreshRepository.save(Refresh.builder()
                            .id(user.getId())
                            .token(token)
                            .timeToLive(refreshExp)
                    .build());
        return token;
    }

    public String passwordTokenGenerator(String email) {
        return generateToken(email, TokenType.PASSWORD);
    }

    private String generateToken(String email, TokenType type) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .claim("type",
                        type.equals(TokenType.ACCESS) ? TokenType.ACCESS.name() :
                                (type.equals(TokenType.REFRESH) ? TokenType.REFRESH.name() : TokenType.PASSWORD.name()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        (type.equals(TokenType.REFRESH) ? refreshExp * 1000L : accessExp * 1000L)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @PreDestroy
    public void redisFlush() {
        authCodeRepository.deleteAll();
        refreshRepository.deleteAll();
    }

    public JwtProvider(DetailsService detailsService, RefreshRepository refreshRepository, AuthCodeRepository authCodeRepository) {
        this.detailsService = detailsService;
        this.refreshRepository = refreshRepository;
        this.authCodeRepository = authCodeRepository;
    }
}