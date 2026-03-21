package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lk.ijse._2_back_end.entity.Vehicle;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double premiumAmount;
    private Double ncbDiscount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // ACTIVE, SUSPENDED, PENDING

    @OneToOne
    private Vehicle vehicle;
}