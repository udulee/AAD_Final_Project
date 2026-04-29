package lk.ijse._2_back_end.service;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String fullName, String username);
    void sendClaimStatusEmail(String toEmail, String fullName, String claimStatus, Long claimId);
    void sendPaymentConfirmation(String toEmail, String fullName, String policyNumber, Double amount, String paymentStatus);
    void sendPolicyRenewalReminder(String toEmail, String fullName, String policyNumber, String expiryDate);
    void sendPasswordResetEmail(String toEmail, String fullName, String resetToken);
}