package lk.ijse._2_back_end.repository;

import lk.ijse._2_back_end.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPolicy_Id(Long policyId);
    List<Payment> findByPaymentStatus(String paymentStatus);
}