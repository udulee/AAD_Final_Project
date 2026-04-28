package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimSubmissionRequest {

    private Long claimId;

    private LocalDate claimDate;
    private String claimStatus;
    private double claimAmount;

    private LocalDate incidentDate;
    private String incidentLocation;
    private String description;

    private String documentPath;

    private LocalDate approvedDate;

    // Foreign key references
    private String vehicleNumber;   // Vehicle FK
    private Long approvedBy;        // User FK (userId)

    // ── Read-only: populated by mapToDTO, never sent by client ──
    private String ownerEmail;      // Vehicle owner's email  (for display)
    private String ownerName;       // Vehicle owner's name   (for display)
}