package lk.ijse._2_back_end.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private long totalPolicies;
    private long activePolicies;
    private long suspendedPolicies;
    private double totalRevenue;
    private long pendingClaims;
    private long approvedClaims;
}
