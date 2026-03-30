package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.ClaimSubmissionRequest;
import lk.ijse._2_back_end.entity.Claim;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.ClaimRepository;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    // Register
    @Override
    public void registerClaim(ClaimSubmissionRequest dto) {
        Claim claim = mapToEntity(dto);
        claimRepository.save(claim);
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

    // Update
    @Override
    public void updateClaim(Long claimId, ClaimSubmissionRequest dto) {
        Claim existing = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found with ID: " + claimId));

        existing.setClaimDate(dto.getClaimDate());
        existing.setClaimStatus(dto.getClaimStatus());
        existing.setClaimAmount(dto.getClaimAmount());
        existing.setIncidentDate(dto.getIncidentDate());
        existing.setIncidentLocation(dto.getIncidentLocation());
        existing.setDescription(dto.getDescription());
        existing.setDocumentPath(dto.getDocumentPath());
        existing.setApprovedDate(dto.getApprovedDate());

        // Update vehicle FK if provided
        if (dto.getVehicleNumber() != null) {
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()));
            existing.setVehicle(vehicle);
        } else {
            existing.setVehicle(null);
        }

        // Update approvedBy FK if provided
        if (dto.getApprovedBy() != null) {
            User user = userRepository.findById(dto.getApprovedBy())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getApprovedBy()));
            existing.setApprovedBy(user);
        } else {
            existing.setApprovedBy(null);
        }

        claimRepository.save(existing);
    }

    // Delete
    @Override
    public void deleteClaim(Long claimId) {
        if (!claimRepository.existsById(claimId)) {
            throw new RuntimeException("Claim not found with ID: " + claimId);
        }
        claimRepository.deleteById(claimId);
    }

    // Reset All
    @Override
    public void resetClaims() {
        claimRepository.deleteAll();
    }

    // Mapping helpers

    private Claim mapToEntity(ClaimSubmissionRequest dto) {
        Claim claim = new Claim();

        claim.setClaimDate(dto.getClaimDate());
        claim.setClaimStatus(dto.getClaimStatus());
        claim.setClaimAmount(dto.getClaimAmount());
        claim.setIncidentDate(dto.getIncidentDate());
        claim.setIncidentLocation(dto.getIncidentLocation());
        claim.setDescription(dto.getDescription());
        claim.setDocumentPath(dto.getDocumentPath());
        claim.setApprovedDate(dto.getApprovedDate());

        // Resolve vehicle FK
        if (dto.getVehicleNumber() != null) {
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()));
            claim.setVehicle(vehicle);
        }

        // Resolve approvedBy FK
        if (dto.getApprovedBy() != null) {
            User user = userRepository.findById(dto.getApprovedBy())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getApprovedBy()));
            claim.setApprovedBy(user);
        }

        return claim;
    }

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

        // Flatten FKs to IDs
        if (claim.getVehicle() != null)
            dto.setVehicleNumber(claim.getVehicle().getVehicleNumber());

        if (claim.getApprovedBy() != null)
            dto.setApprovedBy(claim.getApprovedBy().getUserId());

        return dto;
    }
}