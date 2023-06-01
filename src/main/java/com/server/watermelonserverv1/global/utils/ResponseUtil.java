package com.server.watermelonserverv1.global.utils;

import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    public static String makeIntro(String content) {
        String result;
        if (content.contains(".") && content.indexOf(".") >= 20 && content.indexOf(".") < 30) {
            result = content.substring(0, content.indexOf(".") + 1);
        } else if (content.contains("?") && content.indexOf("?") >= 20 && content.indexOf("?") < 30) {
            result = content.substring(0, content.indexOf("?") + 1);
        } else if (content.contains("!") && content.indexOf("!") >= 20 && content.indexOf("!") < 30) {
            result = content.substring(0, content.indexOf("!") + 1);
        } else {
            if (content.length() < 20) result = content;
            else result = content.substring(0, 20) + "...";
        }
        return result;
    }
}
