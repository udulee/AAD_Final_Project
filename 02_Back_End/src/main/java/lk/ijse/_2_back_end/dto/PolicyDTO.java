package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal premiumAmount;
    private BigDecimal coverageAmount;
    private BigDecimal deductibleAmount;
    private String status;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    private String vehicleNumber;
    private Long userId;
    private String username;
}