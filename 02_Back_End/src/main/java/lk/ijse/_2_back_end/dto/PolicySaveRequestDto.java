package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicySaveRequestDto {

    private String policyNumber;
    private String policyType;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String vehicleNumber;
    private Long customerId;
    private BigDecimal premiumAmount;
    private BigDecimal coverageAmount;
    private BigDecimal deductibleAmount;
}