package com.server.watermelonserverv1.global.security;

import com.server.watermelonserverv1.global.security.details.DetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${token.secret}")
    private String secret;

    private final DetailsService detailsService;

    private final byte[] encodingKey = Base64.getEncoder().encodeToString(secret.getBytes()).getBytes();

    public Authentication generateAuthentication(String accountId) {
        UserDetails details = detailsService.loadUserByUsername(accountId);
        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(encodingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
