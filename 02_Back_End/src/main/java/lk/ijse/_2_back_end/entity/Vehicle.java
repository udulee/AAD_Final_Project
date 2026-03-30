package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    // Basic Identification
    @Id
    private String vehicleNumber;
    private String make;
    private String model;

    // Premium Calculation Factors
    private String vehicleType;
    private Integer manufacturedYear;
    private Double marketValue;
    private String engineNumber;
    private String chassisNumber;

    // Risk & Behavior Assessment
    private String fuelType;
    private String usageType;

    // Ownership Link
    private Long customerId;

}