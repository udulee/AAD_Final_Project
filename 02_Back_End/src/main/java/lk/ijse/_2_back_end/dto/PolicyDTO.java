package lk.ijse._2_back_end.dto;

import jakarta.persistence.ManyToOne;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {
    private VehicleRequestDTO vehicleRequestDTO;
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

    private Vehicle vehicleNumber;

    private User userId;

    public String getVehicleId() {
      return vehicleRequestDTO.getVehicleNumber();
    }
}