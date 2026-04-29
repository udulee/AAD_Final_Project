package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.PolicyDTO;
import lk.ijse._2_back_end.dto.PolicySaveRequestDto;
import lk.ijse._2_back_end.entity.Policy;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.PolicyRepository;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Override
    public void registerPolicy(PolicySaveRequestDto dto) {
        if (dto.getPolicyNumber() != null && policyRepository.existsByPolicyNumber(dto.getPolicyNumber())) {
            throw new RuntimeException("Policy number already exists");
        }

        Policy policy = new Policy();
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setPolicyType(dto.getPolicyType());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPremiumAmount(dto.getPremiumAmount());
        policy.setCoverageAmount(dto.getCoverageAmount());
        policy.setDeductibleAmount(dto.getDeductibleAmount());
        policy.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        policy.setCreatedDate(LocalDate.now());

        if (dto.getVehicleNumber() != null) {
            Vehicle vehicle = vehicleRepository.findByVehicleNumber(dto.getVehicleNumber());
            policy.setVehicle(vehicle);
        }

        if (dto.getCustomerId() != null) {
            User user = userRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            policy.setUser(user);
        }

        policyRepository.save(policy);
    }

    @Override
    public List<PolicyDTO> getAllPolicies() {
        return policyRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PolicyDTO getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + id));
        return mapToDTO(policy);
    }

    @Override
    public void updatePolicy(Long id, PolicyDTO dto) {
        Policy existing = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + id));

        existing.setPolicyNumber(dto.getPolicyNumber());
        existing.setPolicyType(dto.getPolicyType());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setPremiumAmount(dto.getPremiumAmount());
        existing.setCoverageAmount(dto.getCoverageAmount());
        existing.setDeductibleAmount(dto.getDeductibleAmount());
        existing.setStatus(dto.getStatus());
        existing.setUpdatedDate(LocalDate.now());

        if (dto.getVehicleNumber() != null) {
            Vehicle vehicle = vehicleRepository.findByVehicleNumber(dto.getVehicleNumber());
            existing.setVehicle(vehicle);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUser(user);
        }

        policyRepository.save(existing);
    }

    @Override
    public void deletePolicy(Long id) {
        if (!policyRepository.existsById(id)) {
            throw new RuntimeException("Policy not found with ID: " + id);
        }
        policyRepository.deleteById(id);
    }

    @Override
    public void resetPolicies() {
        policyRepository.deleteAll();
    }

    @Override
    public Double getTotalRevenue() {
        Double total = policyRepository.sumTotalRevenue();
        return total != null ? total : 0.0;
    }

    @Override
    public long getTotalPolicies() {
        return policyRepository.count();
    }

    @Override
    public long getActivePolicies() {
        return policyRepository.countByStatus("ACTIVE");
    }

    @Override
    public long getSuspendedPolicies() {
        return policyRepository.countByStatus("SUSPENDED");
    }

    private PolicyDTO mapToDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setPolicyId(policy.getPolicyId());
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setPolicyType(policy.getPolicyType());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setPremiumAmount(policy.getPremiumAmount());
        dto.setCoverageAmount(policy.getCoverageAmount());
        dto.setDeductibleAmount(policy.getDeductibleAmount());
        dto.setStatus(policy.getStatus());
        dto.setCreatedDate(policy.getCreatedDate());
        dto.setUpdatedDate(policy.getUpdatedDate());

        if (policy.getVehicle() != null) {
            dto.setVehicleNumber(policy.getVehicle().getVehicleNumber());
        }

        if (policy.getUser() != null) {
            dto.setUserId(policy.getUser().getUserId());
            dto.setUsername(policy.getUser().getUsername());
        }

        return dto;
    }
}