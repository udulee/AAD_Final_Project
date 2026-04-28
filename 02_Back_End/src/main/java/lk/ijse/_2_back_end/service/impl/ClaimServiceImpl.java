package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.ClaimSubmissionRequest;
import lk.ijse._2_back_end.entity.Claim;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.ClaimRepository;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.ClaimService;
import lk.ijse._2_back_end.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository   claimRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository    userRepository;
    private final EmailService      emailService;   // injected

    // Register
    @Override
    public void registerClaim(ClaimSubmissionRequest dto) {

        Claim claim = new Claim();
        claim.setClaimDate(dto.getClaimDate());
        claim.setClaimStatus(dto.getClaimStatus());
        claim.setClaimAmount(dto.getClaimAmount());
        claim.setIncidentDate(dto.getIncidentDate());
        claim.setIncidentLocation(dto.getIncidentLocation());
        claim.setDescription(dto.getDescription());
        claim.setDocumentPath(dto.getDocumentPath());
        claim.setApprovedDate(dto.getApprovedDate());

        Vehicle vehicle = null;
        if (dto.getVehicleNumber() != null) {
            vehicle = vehicleRepository.findById(dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException(
                            "Vehicle not found: " + dto.getVehicleNumber()));
            claim.setVehicle(vehicle);
        }

        if (dto.getApprovedBy() != null) {
            User user = userRepository.findById(dto.getApprovedBy())
                    .orElseThrow(() -> new RuntimeException(
                            "User not found with ID: " + dto.getApprovedBy()));
            claim.setApprovedBy(user);
        }

        Claim saved = claimRepository.save(claim);

        // Send email to vehicle owner
        if (vehicle != null && vehicle.getUser() != null) {
            User owner = vehicle.getUser();
            tryEmail(owner, saved.getClaimId(), saved.getClaimStatus(),
                    "registered", dto.getVehicleNumber());
        }
    }

    // Get All
    @Override
    @Transactional(readOnly = true)
    public List<ClaimSubmissionRequest> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get By ID
    @Override
    @Transactional(readOnly = true)
    public ClaimSubmissionRequest getClaimById(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException(
                        "Claim not found with ID: " + claimId));
        return mapToDTO(claim);
    }

    // Update
    @Override
    public void updateClaim(Long claimId, ClaimSubmissionRequest dto) {

        Claim existing = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException(
                        "Claim not found with ID: " + claimId));

        String oldStatus = existing.getClaimStatus();   // track status change

        existing.setClaimDate(dto.getClaimDate());
        existing.setClaimStatus(dto.getClaimStatus());
        existing.setClaimAmount(dto.getClaimAmount());
        existing.setIncidentDate(dto.getIncidentDate());
        existing.setIncidentLocation(dto.getIncidentLocation());
        existing.setDescription(dto.getDescription());
        existing.setDocumentPath(dto.getDocumentPath());
        existing.setApprovedDate(dto.getApprovedDate());

        // Update vehicle FK
        Vehicle vehicle = null;
        if (dto.getVehicleNumber() != null) {
            vehicle = vehicleRepository.findById(dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException(
                            "Vehicle not found: " + dto.getVehicleNumber()));
        }
        existing.setVehicle(vehicle);

        // Update approvedBy FK
        existing.setApprovedBy(dto.getApprovedBy() != null
                ? userRepository.findById(dto.getApprovedBy())
                .orElseThrow(() -> new RuntimeException(
                        "User not found with ID: " + dto.getApprovedBy()))
                : null);

        claimRepository.save(existing);

        // Email owner only when status actually changed
        boolean statusChanged = dto.getClaimStatus() != null
                && !dto.getClaimStatus().equals(oldStatus);

        if (statusChanged && vehicle != null && vehicle.getUser() != null) {
            tryEmail(vehicle.getUser(), claimId,
                    dto.getClaimStatus(), "updated", dto.getVehicleNumber());
        }
    }

    // Delete
    @Override
    public void deleteClaim(Long claimId) {
        if (!claimRepository.existsById(claimId))
            throw new RuntimeException("Claim not found with ID: " + claimId);
        claimRepository.deleteById(claimId);
    }

    // Reset
    @Override
    public void resetClaims() {
        claimRepository.deleteAll();
    }

    // Private helpers

    /* Fire-and-forget email; never lets an SMTP failure bubble up. */
    private void tryEmail(User owner, Long claimId,
                          String status, String action, String vehicleNo) {
        try {
            String email    = owner.getEmail();
            String fullName = owner.getFullName() != null
                    ? owner.getFullName() : owner.getUsername();

            if (email == null || email.isBlank()) {
                log.warn("Owner of vehicle {} has no email — skipping notification", vehicleNo);
                return;
            }
            emailService.sendClaimStatusEmail(email, fullName, status, claimId);
            log.info("Claim {} email ({}) sent to {} for claim #{}",
                    action, status, email, claimId);
        } catch (Exception ex) {
            log.error("Email notification failed for claim #{}: {}", claimId, ex.getMessage());
        }
    }

    /* Maps a Claim entity → DTO, flattening FK references. */
    private ClaimSubmissionRequest mapToDTO(Claim claim) {
        ClaimSubmissionRequest dto = new ClaimSubmissionRequest();
        dto.setClaimId(claim.getClaimId());
        dto.setClaimDate(claim.getClaimDate());
        dto.setClaimStatus(claim.getClaimStatus());
        dto.setClaimAmount(claim.getClaimAmount());
        dto.setIncidentDate(claim.getIncidentDate());
        dto.setIncidentLocation(claim.getIncidentLocation());
        dto.setDescription(claim.getDescription());
        dto.setDocumentPath(claim.getDocumentPath());
        dto.setApprovedDate(claim.getApprovedDate());

        if (claim.getVehicle() != null) {
            dto.setVehicleNumber(claim.getVehicle().getVehicleNumber());

            // Expose owner contact for UI display
            if (claim.getVehicle().getOwnerId() != null) {
                User owner = claim.getVehicle().getUser();
                dto.setOwnerEmail(owner.getEmail());
                dto.setOwnerName(owner.getFullName() != null
                        ? owner.getFullName() : owner.getUsername());
            }
        }

        if (claim.getApprovedBy() != null)
            dto.setApprovedBy(claim.getApprovedBy().getUserId());

        return dto;
    }
}