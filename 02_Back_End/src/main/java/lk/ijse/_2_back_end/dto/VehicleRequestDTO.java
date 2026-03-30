package lk.ijse._2_back_end.dto;

import lombok.Data;

@Data
public class VehicleRequestDTO {
    // Basic Identification
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

    // Getters (Lombok should generate these, but being explicit for reliability)
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Integer getManufacturedYear() {
        return manufacturedYear;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public String getUsageType() {
        return usageType;
    }

    public Long getCustomerId() {
        return customerId;
    }
}
