package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chassis;
    private String vehicleNo;
    private String vehicleType;
    private String model;
    private String fuelType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; // Vehicle eka ayithi customer


    public Vehicle() {

    }

    public Vehicle(String chassis, String vehicleNo, String vehicleType, String model, String fuelType) {
        this.chassis=chassis;
        this.vehicleNo=vehicleNo;
        this.vehicleType=vehicleType;
        this.model=model;
        this.fuelType=fuelType;
    }
}