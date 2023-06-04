package com.server.watermelonserverv1.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        // "/auth"
                        .antMatchers(HttpMethod.DELETE, "/auth/logout")
                        .authenticated()

                        // "/user"
                        .antMatchers(HttpMethod.GET, "/user/my-page")
                        .authenticated()
                        .antMatchers(HttpMethod.POST, "/user/mail/password")
                        .authenticated()
                        .antMatchers(HttpMethod.GET, "/user/password")
                        .authenticated()
                        .antMatchers(HttpMethod.PUT, "/user/password")
                        .authenticated()
                        .antMatchers(HttpMethod.PUT, "/user/reissue")
                        .authenticated()

                        // "/post/auth"
                        .antMatchers(HttpMethod.POST, "/post/auth/posting")
                        .authenticated()
                        .antMatchers(HttpMethod.PUT, "/post/auth/{id}")
                        .authenticated()
                        .antMatchers(HttpMethod.DELETE, "/post/auth/{id}")
                        .authenticated()

                        // "/region"
                        .antMatchers(HttpMethod.POST, "/region/setting/{region}")
                        .hasAuthority("ADMIN")

                        .anyRequest().denyAll()
                )
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // if you implement permitAll(), this method better than configure in HttpSecurity
        return web -> web.ignoring()
                // when get cors header to preflight request, avoid the request
                .requestMatchers(CorsUtils::isPreFlightRequest)

                // "/auth"
//                .antMatchers(HttpMethod.GET, "/auth/email/verification")
                .antMatchers(HttpMethod.POST, "/auth/email/transmission")
                .antMatchers(HttpMethod.GET, "/auth/nickname/{nickname}")
                .antMatchers(HttpMethod.POST, "/auth/sign-up")
                .antMatchers(HttpMethod.POST, "/auth/login")

                // "/post/anonymous"
                .antMatchers(HttpMethod.GET, "/post/anonymous")
                .antMatchers(HttpMethod.GET, "/post/anonymous/{id}")
                .antMatchers(HttpMethod.GET, "/post/anonymous/region")
                .antMatchers(HttpMethod.POST, "/post/anonymous/posting")
                .antMatchers(HttpMethod.PUT, "/post/anonymous/{id}")
                .antMatchers(HttpMethod.DELETE, "/post/anonymous/{id}")

                // "/post/auth"
                .antMatchers(HttpMethod.GET, "/post/auth")
                .antMatchers(HttpMethod.GET, "/post/auth/{id}")
                .antMatchers(HttpMethod.GET, "/post/auth/region")

                // "/region"
                .antMatchers(HttpMethod.PUT, "/region")

                .antMatchers(HttpMethod.GET, "/main-page")
        ;
    }
}
