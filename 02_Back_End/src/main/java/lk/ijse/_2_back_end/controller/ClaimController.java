package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.dto.ClaimSubmissionRequest;
import lk.ijse._2_back_end.service.ClaimService;
import lk.ijse._2_back_end.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/claim")
@CrossOrigin
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping("register")
    public ResponseEntity<APIResponse> registerClaim(
            @RequestBody ClaimSubmissionRequest dto) {
        try {
            claimService.registerClaim(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse(201,
                            "Claim registered successfully. " +
                                    "A notification email has been sent to the vehicle owner.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new APIResponse(409, e.getMessage(), null));
        }
    }

    @GetMapping("all")
    public ResponseEntity<APIResponse> getAllClaims() {
        try {
            List<ClaimSubmissionRequest> list = claimService.getAllClaims();
            return ResponseEntity.ok(new APIResponse(200, "Success", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("{claimId}")
    public ResponseEntity<APIResponse> getClaimById(@PathVariable Long claimId) {
        try {
            return ResponseEntity.ok(new APIResponse(200, "Success",
                    claimService.getClaimById(claimId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    @PutMapping("update/{claimId}")
    public ResponseEntity<APIResponse> updateClaim(
            @PathVariable Long claimId,
            @RequestBody ClaimSubmissionRequest dto) {
        try {
            claimService.updateClaim(claimId, dto);
            return ResponseEntity.ok(new APIResponse(200,
                    "Claim updated. " +
                            "If the status changed, a notification email has been sent.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("delete/{claimId}")
    public ResponseEntity<APIResponse> deleteClaim(@PathVariable Long claimId) {
        try {
            claimService.deleteClaim(claimId);
            return ResponseEntity.ok(new APIResponse(200, "Claim deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("reset")
    public ResponseEntity<APIResponse> resetClaims() {
        try {
            claimService.resetClaims();
            return ResponseEntity.ok(new APIResponse(200, "All claims deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }
}