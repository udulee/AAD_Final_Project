package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.PolicyDTO;
import lk.ijse._2_back_end.entity.Policy;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.PolicyRepository;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    // ========================= CRUD =========================

    @Override
    public void registerPolicy(PolicyDTO dto) {

        if (dto.getPolicyNumber() != null &&
                policyRepository.existsByPolicyNumber(dto.getPolicyNumber())) {
            throw new RuntimeException("Policy number already exists");
        }

        Policy po = new Policy();

        po.setPolicyNumber(dto.getPolicyNumber());
        po.setPolicyType(dto.getPolicyType());
        po.setStartDate(dto.getStartDate());
        po.setEndDate(dto.getEndDate());
        po.setPremiumAmount(dto.getPremiumAmount());
        po.setCoverageAmount(dto.getCoverageAmount());
        po.setDeductibleAmount(dto.getDeductibleAmount());
        po.setStatus(dto.getStatus());

        // ✅ Fetch related entities
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        User user = (User) userRepository.findById(dto.getUserId().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        po.setVehicle(vehicle);
        po.setUser(user);

        policyRepository.save(po);
    }

    @Override
    public List getAllPolicies() {
        return List.of();
    }

    @Override
    public void getPolicyById() {

    }

    @Override
    public void updatePolicy(Long id, PolicyDTO dto) {

    }

    @Override
    public void deletePolicy(Long id) {

    }

    @Override
    public void resetPolicies() {

    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<PolicyDTO> getAllPolicies() {
//        return policyRepository.findAll()
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public PolicyDTO getPolicyById() {
//        Policy policy = policyRepository.findById(policyId)
//                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + policyId));
//        return mapToDTO(policy);
//    }
//
//    @Override
//    public void updatePolicy(Long policyId, PolicyDTO dto) {
//        Policy existing = policyRepository.findById(policyId)
//                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + policyId));
//
//        existing.setPolicyNumber(dto.getPolicyNumber());
//        existing.setPolicyType(dto.getPolicyType());
//        existing.setStartDate(dto.getStartDate());
//        existing.setEndDate(dto.getEndDate());
//        existing.setPremiumAmount(dto.getPremiumAmount());
//        existing.setCoverageAmount(dto.getCoverageAmount());
//        existing.setDeductibleAmount(dto.getDeductibleAmount());
//        existing.setStatus(dto.getStatus());
//        existing.setUpdatedDate(LocalDate.now());
//
//        // Vehicle
//        if (dto.getVehicleNumber() != null) {
//            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleNumber())
//                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()));
//            existing.setVehicle(vehicle);
//        } else {
//            existing.setVehicle(null);
//        }
//
//        // User
//        if (dto.getUserId() != null) {
//            User customer = userRepository.findById(dto.getUserId())
//                    .orElseThrow(() -> new RuntimeException("Customer not found: " + dto.getUserId()));
//            existing.setUser(customer);
//        } else {
//            existing.setUser(null);
//        }
//
//        policyRepository.save(existing);
//    }
//
//    @Override
//    public void deletePolicy(Long policyId) {
//        if (!policyRepository.existsById(policyId)) {
//            throw new RuntimeException("Policy not found with ID: " + policyId);
//        }
//        policyRepository.deleteById(policyId);
//    }
//
//    @Override
//    public void resetPolicies() {
//        policyRepository.deleteAll();
//    }
//
//    // ========================= DASHBOARD (🔥 NEW) =========================
//
//    @Override
//    public Double getTotalRevenue() {
//        Double total = policyRepository.sumTotalRevenue();
//        return total != null ? total : 0.0; // ✅ avoid null crash
//    }
//
//    @Override
//    public long getTotalPolicies() {
//        return policyRepository.count();
//    }
//
//    @Override
//    public long getActivePolicies() {
//        return policyRepository.countByStatus("ACTIVE");
//    }
//
//    @Override
//    public Object getSuspendedPolicies() {
//        return policyRepository.countByStatus("SUSPENDED");
//    }
//
//    // ========================= MAPPERS =========================
//
//    private PolicyDTO mapToDTO(Policy policy) {
//        PolicyDTO dto = new PolicyDTO();
//
//        dto.setPolicyId(policy.getPolicyId());
//        dto.setPolicyNumber(policy.getPolicyNumber());
//        dto.setPolicyType(policy.getPolicyType());
//        dto.setStartDate(policy.getStartDate());
//        dto.setEndDate(policy.getEndDate());
//        dto.setPremiumAmount(policy.getPremiumAmount());
//        dto.setCoverageAmount(policy.getCoverageAmount());
//        dto.setDeductibleAmount(policy.getDeductibleAmount());
//        dto.setStatus(policy.getStatus());
//        dto.setCreatedDate(policy.getCreatedDate());
//        dto.setUpdatedDate(policy.getUpdatedDate());
//
//        if (policy.getVehicle() != null)
//            dto.setVehicleNumber(policy.getVehicle().getVehicleNumber());
//
//        if (policy.getUser() != null)
//            dto.setUserId(policy.getUser().getUserId());
//
//        return dto;
//    }
//
//    private Policy mapToEntity(PolicyDTO dto) {
//        Policy policy = new Policy();
//
//        policy.setPolicyNumber(dto.getPolicyNumber());
//        policy.setPolicyType(dto.getPolicyType());
//        policy.setStartDate(dto.getStartDate());
//        policy.setEndDate(dto.getEndDate());
//        policy.setPremiumAmount(dto.getPremiumAmount());
//        policy.setCoverageAmount(dto.getCoverageAmount());
//        policy.setDeductibleAmount(dto.getDeductibleAmount());
//        policy.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
//
//        if (dto.getVehicleNumber() != null) {
//            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleNumber())
//                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()));
//            policy.setVehicle(vehicle);
//        }
//
//        if (dto.getUserId() != null) {
//            User customer = userRepository.findById(dto.getUserId())
//                    .orElseThrow(() -> new RuntimeException("Customer not found: " + dto.getUserId()));
//            policy.setUser(customer);
//        }
//
//        return policy;
//    }
}