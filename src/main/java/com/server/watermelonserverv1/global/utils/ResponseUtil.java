package com.server.watermelonserverv1.global.utils;

import com.server.watermelonserverv1.domain.auth.exception.MessageConfigException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class ResponseUtil {

    private final JavaMailSender javaMailSender;

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

    @Async
    public void sendMail(String destinationAddress, String mailDescription) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
        try {
            helper.setTo(destinationAddress);
            helper.setSubject("send to watermelon picnic...");
            helper.setText(mailDescription, true);
        } catch (MessagingException e) { throw MessageConfigException.EXCEPTION; }
        javaMailSender.send(mimeMessage);
    }
}
