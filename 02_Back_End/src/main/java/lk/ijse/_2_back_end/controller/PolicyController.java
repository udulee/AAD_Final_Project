package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.dto.PolicyDTO;
import lk.ijse._2_back_end.dto.PolicySaveRequestDto;
import lk.ijse._2_back_end.service.PolicyService;
import lk.ijse._2_back_end.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/policies")
@CrossOrigin(origins = "*")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    //  CREATE
    @PostMapping("/save")
    public ResponseEntity<APIResponse> registerPolicy(@RequestBody PolicySaveRequestDto dto) {
        try {
            policyService.registerPolicy(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(201,"Policy registered successfully!",null));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse(500,e.getMessage(),null));
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<?> getAllPolicies() {
        try {
            List<PolicyDTO> list = policyService.getAllPolicies();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // READ BY ID
    @GetMapping("/{id}")
    public void getPolicyById(@PathVariable Long id) {
        policyService.getPolicyById();
//        try {
//            return ResponseEntity.ok(policyService.getPolicyById());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
    }

    //  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePolicy(@PathVariable Long id, @RequestBody PolicyDTO dto) {
        try {
            policyService.updatePolicy(id, dto);
            return ResponseEntity.ok("Policy updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePolicy(@PathVariable Long id) {
        try {
            policyService.deletePolicy(id);
            return ResponseEntity.ok("Policy deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ✅ RESET
    @DeleteMapping("/reset")
    public ResponseEntity<?> resetPolicies() {
        try {
            policyService.resetPolicies();
            return ResponseEntity.ok("All policies reset successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 🔥 NEW DASHBOARD API (IMPORTANT)
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        try {
//            Map<String, > data = new HashMap<>();
//
//            data.put("totalRevenue", policyService.getPolicyById());
//            data.put("totalPolicies", policyService.getTotalPolicies());
//            data.put("activePolicies", policyService.getActivePolicies());
//            data.put("suspendedPolicies", policyService.getSuspendedPolicies());

          //  return ResponseEntity.ok(data);
return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}