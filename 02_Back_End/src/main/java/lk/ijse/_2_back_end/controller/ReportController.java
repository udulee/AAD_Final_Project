package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // Total policies
    @GetMapping("/total-policies")
    public ResponseEntity<?> getTotalPolicies() {
        return ResponseEntity.ok(reportService.getTotalPolicies());
    }

    // Active vs Suspended
    @GetMapping("/policy-status")
    public ResponseEntity<?> getPolicyStatus() {
        return ResponseEntity.ok(reportService.getPolicyStatus());
    }

    // Total revenue
    @GetMapping("/revenue")
    public ResponseEntity<?> getRevenue() {
        return ResponseEntity.ok(reportService.getTotalRevenue());
    }

    // Claims report
    @GetMapping("/claims")
    public ResponseEntity<?> getClaimsReport() {
        return ResponseEntity.ok(reportService.getClaimsReport());
    }
}
