package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.PaymentRequest;
import lk.ijse._2_back_end.entity.InsurancePolicy;
import lk.ijse._2_back_end.entity.Payment;
import lk.ijse._2_back_end.repository.PaymentRepository;
import lk.ijse._2_back_end.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

//    @Autowired
//    private InsurancePolicyRepository insurancePolicyRepository;

    @Override
    public void registerPayment(PaymentRequest dto) {
        Payment payment = mapToEntity(dto);
        paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentRequest> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentRequest getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        return mapToDTO(payment);
    }

    @Override
    public void updatePayment(Long id, PaymentRequest dto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));

        existing.setAmount(dto.getAmount());
        existing.setDueDate(dto.getDueDate());
        existing.setPaidDate(dto.getPaidDate());
        existing.setPaymentStatus(dto.getPaymentStatus());

//        if (dto.getPolicyId() != null) {
//            InsurancePolicy policy = insurancePolicyRepository.findById(dto.getPolicyId())
//                    .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + dto.getPolicyId()));
//            existing.setPolicy(policy);
//        } else {
//        }
           existing.setPolicy(null);

        paymentRepository.save(existing);
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public void resetPayments() {
        paymentRepository.deleteAll();
    }

    private Payment mapToEntity(PaymentRequest dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setDueDate(dto.getDueDate());
        payment.setPaidDate(dto.getPaidDate());
        payment.setPaymentStatus(dto.getPaymentStatus() != null ? dto.getPaymentStatus() : "PENDING");

//        if (dto.getPolicyId() != null) {
//            InsurancePolicy policy = insurancePolicyRepository.findById(dto.getPolicyId())
//                    .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + dto.getPolicyId()));
//            payment.setPolicy(policy);
//        }

        return payment;
    }

    // ── Entity → DTO ──
    private PaymentRequest mapToDTO(Payment payment) {
        PaymentRequest dto = new PaymentRequest();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setDueDate(payment.getDueDate());
        dto.setPaidDate(payment.getPaidDate());
        dto.setPaymentStatus(payment.getPaymentStatus());

        if (payment.getPolicy() != null) {
            dto.setPolicyId(payment.getPolicy().getId());
        }

        return dto;
    }
}