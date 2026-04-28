package lk.ijse._2_back_end.service;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String fullName, String username);
    void sendClaimStatusEmail(String toEmail, String fullName, String claimStatus, Long claimId);
}
