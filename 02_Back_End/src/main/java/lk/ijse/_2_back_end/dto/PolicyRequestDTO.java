package lk.ijse._2_back_end.dto;

import lombok.Data;

@Data
public class PolicyRequestDTO {
    private Long vehicleId;
    private Integer installmentPlan; // 1 (Full), 3, or 6
}
