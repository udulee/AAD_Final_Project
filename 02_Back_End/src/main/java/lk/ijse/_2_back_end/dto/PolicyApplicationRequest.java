package lk.ijse._2_back_end.dto;

import lombok.Data;

@Data
public class PolicyApplicationRequest {
    private Long vehicleId;
    private Integer installmentPlan;
}
