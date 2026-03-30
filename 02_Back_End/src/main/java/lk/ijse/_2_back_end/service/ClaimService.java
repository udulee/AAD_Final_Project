package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.ClaimSubmissionRequest;

import java.util.List;

public interface ClaimService {

    void registerClaim(ClaimSubmissionRequest dto);

    List<ClaimSubmissionRequest> getAllClaims();

    void updateClaim(Long claimId, ClaimSubmissionRequest dto);

    void deleteClaim(Long claimId);

    void resetClaims();
}