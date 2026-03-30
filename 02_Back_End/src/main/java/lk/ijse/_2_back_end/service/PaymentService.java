package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.PaymentRequest;

import java.util.List;

public interface PaymentService {
    void registerPayment(PaymentRequest dto);
    List<PaymentRequest> getAllPayments();
    PaymentRequest getPaymentById(Long id);
    void updatePayment(Long id, PaymentRequest dto);
    void deletePayment(Long id);
    void resetPayments();
}