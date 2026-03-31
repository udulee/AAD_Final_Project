package lk.ijse._2_back_end.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {
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

    // Flattened FKs
    private String vehicleNumber;
    private Long userId;
}