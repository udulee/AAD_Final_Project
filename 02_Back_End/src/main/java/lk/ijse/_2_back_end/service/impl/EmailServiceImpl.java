package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    //  @Async so registration doesn't block waiting for SMTP
    @Async
    @Override
    public void sendWelcomeEmail(String toEmail, String fullName, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Welcome to Vehicle Insurance System!");
            message.setText(
                "Dear " + fullName + ",\n\n" +
                "Your account has been created successfully.\n\n" +
                "Username: " + username + "\n\n" +
                "You can now log in and manage your vehicles and policies.\n\n" +
                "Thank you for joining us!\n\n" +
                "— Vehicle Insurance Team"
            );
            mailSender.send(message);
            log.info("Welcome email sent to {}", toEmail);
        } catch (Exception e) {
            // Log but don't fail registration if email fails
            log.error("Failed to send welcome email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendClaimStatusEmail(String toEmail, String fullName, String claimStatus, Long claimId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Claim #" + claimId + " Status Update");
            message.setText(
                "Dear " + fullName + ",\n\n" +
                "Your claim #" + claimId + " status has been updated to: " + claimStatus + "\n\n" +
                "Please log in to your account for more details.\n\n" +
                "— Vehicle Insurance Team"
            );
            mailSender.send(message);
            log.info("Claim status email sent to {} for claim #{}", toEmail, claimId);
        } catch (Exception e) {
            log.error("Failed to send claim email to {}: {}", toEmail, e.getMessage());
        }
    }
}
