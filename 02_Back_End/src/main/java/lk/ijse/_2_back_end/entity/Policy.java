package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lk.ijse._2_back_end.entity.Claim;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    private BigDecimal premiumAmount;
    private BigDecimal coverageAmount;
    private BigDecimal deductibleAmount;

    private String status;

    private LocalDate createdDate;
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "policy")
    private List<Claim> claims;
}