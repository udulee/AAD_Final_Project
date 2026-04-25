package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleRequestDTO {
    //  Identification
    private String vehicleNumber;
    private String make;
    private String model;

    // Premium Calculation
    private String vehicleType;
    private Integer manufacturedYear;
    private Double marketValue;
    private String engineNumber;
    private String chassisNumber;

    // Risk & Behavior Assessment
    private String fuelType;
    private String usageType;

    // Ownership Link
    private Long ownerId;
}
