package com.server.watermelonserverv1.global.security;

import com.server.watermelonserverv1.global.security.details.DetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    private final DetailsService detailsService;

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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
    public JwtProvider(DetailsService detailsService) {
        this.detailsService = detailsService;
    }
}