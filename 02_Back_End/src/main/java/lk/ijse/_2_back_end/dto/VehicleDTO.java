package lk.ijse._2_back_end.dto;

import lombok.Data;

@Data
public class VehicleDTO {
    private String chassis;
    private String vehicleNo;
    private String vehicleType;
    private String model;
    private String fuelType;
}