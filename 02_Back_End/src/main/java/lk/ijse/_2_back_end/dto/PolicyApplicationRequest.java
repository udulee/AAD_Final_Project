package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyApplicationRequest {
    private Long vehicleId;
    private Integer installmentPlan;
}
