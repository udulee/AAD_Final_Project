//package lk.ijse._2_back_end.service;
//
//import lk.ijse._2_back_end.repository.ClaimRepository;
//import lk.ijse._2_back_end.repository.PaymentRepository;
//import lk.ijse._2_back_end.repository.PolicyRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class ReportService {
//
//    private final PolicyRepository policyRepo;
//    private final PaymentRepository paymentRepo;
//    private final ClaimRepository claimRepo;
//
//    public long getTotalPolicies() {
//        return policyRepo.count();
//    }
//
//    public Map<String, Long> getPolicyStatus() {
//        long active = policyRepo.countByStatus("ACTIVE");
//        long suspended = policyRepo.countByStatus("SUSPENDED");
//
//        Map<String, Long> map = new HashMap<>();
//        map.put("active", active);
//        map.put("suspended", suspended);
//
//        return map;
//    }
//
//    public double getTotalRevenue() {
//        return paymentRepo.sumAllPayments();
//    }
//
//    public Map<String, Long> getClaimsReport() {
//        long pending = claimRepo.countByStatus("PENDING");
//        long approved = claimRepo.countByStatus("APPROVED");
//
//        Map<String, Long> map = new HashMap<>();
//        map.put("pending", pending);
//        map.put("approved", approved);
//
//        return map;
//    }
//}
