package org.oss.LibraryManagementSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;
    @Async
    public void sendEmail(String to, String emailSubject, String emailBody) {
        try {
            var mimeMessage = javaMailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(emailBody, true);
            mimeMessageHelper.setSubject(emailSubject);

            // Send mail
            javaMailSender.send(mimeMessage);
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            throw new RuntimeException("error while sending email");
        }
    }
}
