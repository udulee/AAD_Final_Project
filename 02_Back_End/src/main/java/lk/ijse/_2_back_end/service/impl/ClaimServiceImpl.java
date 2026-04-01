package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.ClaimSubmissionRequest;
import lk.ijse._2_back_end.entity.Claim;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.ClaimRepository;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // ✅ UserServiceImpl style
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;      // ✅ all final — no @Autowired
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;              // ✅ inject like UserServiceImpl

    // ➕ Register Claim
    @Override
    public void registerClaim(ClaimSubmissionRequest dto) {
        claimRepository.save(mapToEntity(dto));
    }

    // 📋 Get All Claims
    @Override
    @Transactional(readOnly = true)
    public List<ClaimSubmissionRequest> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map((data) -> mapToDTO(data))          // ✅ UserServiceImpl stream style
                .collect(Collectors.toList());
    }

    // 🔍 Get Claim By ID
    @Override
    public ClaimSubmissionRequest getClaimById(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found with ID: " + claimId));
        return mapToDTO(claim);
    }

    // ✏️ Update Claim
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

        // Update vehicle FK
        existing.setVehicle(dto.getVehicleNumber() != null
                ? vehicleRepository.findById(dto.getVehicleNumber())
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()))
                : null);

        // Update approvedBy FK
        existing.setApprovedBy(dto.getApprovedBy() != null
                ? userRepository.findById(dto.getApprovedBy())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getApprovedBy()))
                : null);

        claimRepository.save(existing);
    }

    // ❌ Delete Claim
    @Override
    public void deleteClaim(Long claimId) {
        if (!claimRepository.existsById(claimId)) {
            throw new RuntimeException("Claim not found with ID: " + claimId);
        }
        claimRepository.deleteById(claimId);
    }

    // 🔄 Reset All Claims
    @Override
    public void resetClaims() {
        claimRepository.deleteAll();
    }

    // ── Mapping Helpers ──────────────────────────────────────────────────────
    // ⚠️ ModelMapper.map() cannot resolve FK relationships (Vehicle → vehicleNumber, User → userId)
    //    because DTO holds IDs but entity holds full objects — manual mapping is intentional here

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

        // Resolve Vehicle FK
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