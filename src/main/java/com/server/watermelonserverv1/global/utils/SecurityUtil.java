package com.server.watermelonserverv1.global.utils;

import com.server.watermelonserverv1.domain.user.domain.User;
import com.server.watermelonserverv1.global.security.details.Details;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public User getContextInfo() {
        Details contextInfo = (Details) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return contextInfo.getUser();
    }
}
