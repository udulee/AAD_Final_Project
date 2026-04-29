package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private final String emailTemplate = """
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
                .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
                .header { background: linear-gradient(135deg, #4f8ef7 0%, #06b6d4 100%); padding: 30px; text-align: center; color: white; }
                .header h1 { margin: 0; font-size: 24px; }
                .content { padding: 30px; color: #333333; line-height: 1.6; }
                .button { display: inline-block; padding: 12px 24px; background: #4f8ef7; color: white; text-decoration: none; border-radius: 6px; margin: 20px 0; }
                .footer { background-color: #f8f8f8; padding: 20px; text-align: center; color: #888888; font-size: 12px; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>🚗 Vehicle Insurance System</h1>
                </div>
                <div class="content">
                    ##CONTENT##
                </div>
                <div class="footer">
                    <p>© 2025 Vehicle Insurance Management System</p>
                    <p>This is an automated message. Please do not reply to this email.</p>
                </div>
            </div>
        </body>
        </html>
        """;

    @Async
    @Override
    public void sendWelcomeEmail(String toEmail, String fullName, String username) {
        try {
            String content = String.format("""
                <h2>Welcome, %s!</h2>
                <p>Your account has been created successfully.</p>
                <p><strong>Username:</strong> %s</p>
                <p>You can now log in to manage your vehicles and policies.</p>
                <a href="%s/pages/dashboard.html" class="button">Login Now</a>
                """, fullName, username, baseUrl);

            sendHtmlEmail(toEmail, "Welcome to Vehicle Insurance System!", content);
            log.info("Welcome email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendClaimStatusEmail(String toEmail, String fullName, String claimStatus, Long claimId) {
        try {
            String statusColor = "APPROVED".equalsIgnoreCase(claimStatus) ? "#2ecc71" : 
                                "REJECTED".equalsIgnoreCase(claimStatus) ? "#e74c3c" : "#f39c12";
            
            String content = String.format("""
                <h2>Claim Status Update</h2>
                <p>Dear %s,</p>
                <p>Your claim <strong>#%d</strong> status has been updated:</p>
                <p style="background: %s; color: white; padding: 10px; border-radius: 5px; display: inline-block;">
                    <strong>%s</strong>
                </p>
                <p>Please log in to your account for more details.</p>
                <a href="%s/pages/claims.html" class="button">View Claims</a>
                """, fullName, claimId, statusColor, claimStatus, baseUrl);

            sendHtmlEmail(toEmail, "Claim #" + claimId + " Status Update", content);
            log.info("Claim status email sent to {} for claim #{}", toEmail, claimId);
        } catch (Exception e) {
            log.error("Failed to send claim email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendPaymentConfirmation(String toEmail, String fullName, String policyNumber, Double amount, String paymentStatus) {
        try {
            String statusColor = "SUCCESS".equalsIgnoreCase(paymentStatus) ? "#2ecc71" : "#e74c3c";
            
            String content = String.format("""
                <h2>Payment Confirmation</h2>
                <p>Dear %s,</p>
                <p>Your payment for policy <strong>%s</strong> has been processed.</p>
                <table style="width: 100%%; margin: 20px 0; border-collapse: collapse;">
                    <tr><td style="padding: 10px; border-bottom: 1px solid #eee;"><strong>Policy Number:</strong></td><td style="padding: 10px; border-bottom: 1px solid #eee;">%s</td></tr>
                    <tr><td style="padding: 10px; border-bottom: 1px solid #eee;"><strong>Amount:</strong></td><td style="padding: 10px; border-bottom: 1px solid #eee;">LKR %, .2f</td></tr>
                    <tr><td style="padding: 10px; border-bottom: 1px solid #eee;"><strong>Status:</strong></td><td style="padding: 10px; border-bottom: 1px solid #eee; color: %s;">%s</td></tr>
                </table>
                <a href="%s/pages/payments.html" class="button">View Payments</a>
                """, fullName, policyNumber, policyNumber, amount, statusColor, paymentStatus, baseUrl);

            sendHtmlEmail(toEmail, "Payment Confirmation - Policy " + policyNumber, content);
            log.info("Payment confirmation email sent to {} for policy {}", toEmail, policyNumber);
        } catch (Exception e) {
            log.error("Failed to send payment email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendPolicyRenewalReminder(String toEmail, String fullName, String policyNumber, String expiryDate) {
        try {
            String content = String.format("""
                <h2>Policy Renewal Reminder</h2>
                <p>Dear %s,</p>
                <p>Your policy <strong>%s</strong> is expiring soon.</p>
                <p style="background: #fff3cd; padding: 15px; border-radius: 5px; border-left: 4px solid #f39c12;">
                    <strong>Expiry Date: %s</strong>
                </p>
                <p>Please renew your policy to continue coverage.</p>
                <a href="%s/pages/policies.html" class="button">Renew Now</a>
                """, fullName, policyNumber, expiryDate, baseUrl);

            sendHtmlEmail(toEmail, "Policy Renewal Reminder - " + policyNumber, content);
            log.info("Policy renewal reminder sent to {} for policy {}", toEmail, policyNumber);
        } catch (Exception e) {
            log.error("Failed to send renewal reminder to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendPasswordResetEmail(String toEmail, String fullName, String resetToken) {
        try {
            String resetUrl = baseUrl + "/pages/reset-password.html?token=" + resetToken;
            
            String content = String.format("""
                <h2>Password Reset Request</h2>
                <p>Dear %s,</p>
                <p>We received a request to reset your password. Click the button below to create a new password:</p>
                <a href="%s" class="button">Reset Password</a>
                <p style="color: #888; font-size: 12px; margin-top: 20px;">
                    This link will expire in 24 hours.<br>
                    If you didn't request this, please ignore this email.
                </p>
                """, fullName, resetUrl);

            sendHtmlEmail(toEmail, "Password Reset - Vehicle Insurance System", content);
            log.info("Password reset email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage());
        }
    }

    private void sendHtmlEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        
        String htmlContent = emailTemplate.replace("##CONTENT##", content);
        helper.setText(htmlContent, true);
        
        mailSender.send(message);
    }
}