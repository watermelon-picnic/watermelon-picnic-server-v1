package com.server.watermelonserverv1.global.security;

import com.server.watermelonserverv1.global.security.details.DetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final DetailsService detailsService;

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${token.exp.access}")
    private Long accessExp;

    @Value("${token.exp.refresh}")
    private Long refreshExp;

    private enum TokenType{
        ACCESS, REFRESH
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

    private String generateToken(String email, TokenType type) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .claim("type",
                        type.equals(TokenType.ACCESS) ? TokenType.ACCESS.name() : TokenType.REFRESH.name())
                .setExpiration(new Date(System.currentTimeMillis() +
                        (type.equals(TokenType.ACCESS) ? accessExp : refreshExp)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtProvider(DetailsService detailsService) {
        this.detailsService = detailsService;
    }
}