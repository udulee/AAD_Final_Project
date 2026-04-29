package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.dto.DashboardResponse;
import lk.ijse._2_back_end.dto.PolicyDTO;
import lk.ijse._2_back_end.dto.PolicySaveRequestDto;
import lk.ijse._2_back_end.service.PolicyService;
import lk.ijse._2_back_end.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/policies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("/save")
    public ResponseEntity<APIResponse> registerPolicy(@RequestBody PolicySaveRequestDto dto) {
        try {
            policyService.registerPolicy(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse(201, "Policy registered successfully!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse(400, e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllPolicies() {
        try {
            List<PolicyDTO> list = policyService.getAllPolicies();
            return ResponseEntity.ok(new APIResponse(200, "OK", list));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getPolicyById(@PathVariable Long id) {
        try {
            PolicyDTO policy = policyService.getPolicyById(id);
            return ResponseEntity.ok(new APIResponse(200, "OK", policy));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updatePolicy(@PathVariable Long id, @RequestBody PolicyDTO dto) {
        try {
            policyService.updatePolicy(id, dto);
            return ResponseEntity.ok(new APIResponse(200, "Policy updated successfully!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse(400, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deletePolicy(@PathVariable Long id) {
        try {
            policyService.deletePolicy(id);
            return ResponseEntity.ok(new APIResponse(200, "Policy deleted successfully!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/reset")
    public ResponseEntity<APIResponse> resetPolicies() {
        try {
            policyService.resetPolicies();
            return ResponseEntity.ok(new APIResponse(200, "All policies reset successfully!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<APIResponse> getDashboard() {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("totalRevenue", policyService.getTotalRevenue());
            data.put("totalPolicies", policyService.getTotalPolicies());
            data.put("activePolicies", policyService.getActivePolicies());
            data.put("suspendedPolicies", policyService.getSuspendedPolicies());
            return ResponseEntity.ok(new APIResponse(200, "OK", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }
}