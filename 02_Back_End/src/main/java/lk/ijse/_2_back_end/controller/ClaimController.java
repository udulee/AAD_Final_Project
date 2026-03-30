package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.dto.ClaimSubmissionRequest;
import lk.ijse._2_back_end.service.ClaimService;
import lk.ijse._2_back_end.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/claim")
@CrossOrigin
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<Void>> registerClaim(@RequestBody ClaimSubmissionRequest dto) {
        try {
            claimService.registerClaim(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse<>(201, "Claim registered successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new APIResponse<>(409, e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<ClaimSubmissionRequest>>> getAllClaims() {
        try {
            List<ClaimSubmissionRequest> list = claimService.getAllClaims();
            return ResponseEntity.ok(new APIResponse<>(200, "Success", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(500, e.getMessage(), null));
        }
    }

    @PutMapping("/update/{claimId}")
    public ResponseEntity<APIResponse<Void>> updateClaim(
            @PathVariable Long claimId,
            @RequestBody ClaimSubmissionRequest dto) {
        try {
            claimService.updateClaim(claimId, dto);
            return ResponseEntity.ok(new APIResponse<>(200, "Claim updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{claimId}")
    public ResponseEntity<APIResponse<Void>> deleteClaim(@PathVariable Long claimId) {
        try {
            claimService.deleteClaim(claimId);
            return ResponseEntity.ok(new APIResponse<>(200, "Claim deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/reset")
    public ResponseEntity<APIResponse<Void>> resetClaims() {
        try {
            claimService.resetClaims();
            return ResponseEntity.ok(new APIResponse<>(200, "All claims deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(500, e.getMessage(), null));
        }
    }
}