package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    private String policyNumber;
    private String policyType;

    private LocalDate startDate;
    private LocalDate endDate;

    private double premiumAmount;
    private double coverageAmount;
    private double deductibleAmount;

    private String status;

    private LocalDate createdDate;
    private LocalDate updatedDate;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "policy")
    private List<Claim> claims;
}