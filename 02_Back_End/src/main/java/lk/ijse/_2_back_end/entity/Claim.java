package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private LocalDate claimDate;
    private String claimStatus;
    private double claimAmount;

    private LocalDate incidentDate;
    private String incidentLocation;
    private String description;

    private String documentPath;

    private LocalDate approvedDate;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;
}
