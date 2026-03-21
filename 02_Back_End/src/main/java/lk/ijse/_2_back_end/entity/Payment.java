package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String paymentStatus; // PAID, OVERDUE, PENDING

    @ManyToOne
    private InsurancePolicy policy;
}
