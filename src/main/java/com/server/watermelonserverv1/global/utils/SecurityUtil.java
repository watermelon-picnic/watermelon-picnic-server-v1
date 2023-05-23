package com.server.watermelonserverv1.global.utils;

import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.global.exception.AuthBadRequestException;
import com.server.watermelonserverv1.global.security.details.Details;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityUtil {

    public User getContextInfo() {
        Details contextInfo = (Details) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (contextInfo == null) throw AuthBadRequestException.EXCEPTION;
        return contextInfo.getUser();
    }

    public String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader("X-FORWARD-FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

    public List<String> getAuthorities() {
        List<? extends GrantedAuthority> authorities = new ArrayList<>(SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities());
        List<String> response = new ArrayList<>();
        for (Object element: authorities) {
            SimpleGrantedAuthority s = (SimpleGrantedAuthority) element;
            response.add(s.getAuthority());
        }
        return response;
    }
}
