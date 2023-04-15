package com.server.watermelonserverv1.global.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }

    private String resolveRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
