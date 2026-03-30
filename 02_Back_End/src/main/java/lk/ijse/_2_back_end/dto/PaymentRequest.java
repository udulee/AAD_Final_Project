package lk.ijse._2_back_end.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long id;
    private Double amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String paymentStatus; // PAID, OVERDUE, PENDING
    private Long policyId;        // InsurancePolicy FK
}