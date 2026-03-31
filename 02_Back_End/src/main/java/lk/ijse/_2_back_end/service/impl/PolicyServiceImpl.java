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

    // ── Register ──
    @Override
    public void registerPolicy(PolicyDTO dto) {
        if (dto.getPolicyNumber() != null &&
                policyRepository.existsByPolicyNumber(dto.getPolicyNumber())) {
            throw new RuntimeException("Policy number already exists: " + dto.getPolicyNumber());
        }
        Policy policy = mapToEntity(dto);
        policy.setCreatedDate(LocalDate.now());
        policy.setUpdatedDate(LocalDate.now());
        policyRepository.save(policy);
    }

    // ── Get All ──
    @Override
    @Transactional(readOnly = true)
    public List<PolicyDTO> getAllPolicies() {
        return policyRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── Get By ID ──
    @Override
    @Transactional(readOnly = true)
    public PolicyDTO getPolicyById(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + policyId));
        return mapToDTO(policy);
    }

    // ── Update ──
    @Override
    public void updatePolicy(Long policyId, PolicyDTO dto) {
        Policy existing = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with ID: " + policyId));

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
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()));
            existing.setVehicle(vehicle);
        } else {
            existing.setVehicle(null);
        }

        if (dto.getUserId() != null) {
            User customer = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + dto.getUserId()));
            existing.setUser(customer);
        } else {
            existing.setUser(null);
        }

        policyRepository.save(existing);
    }

    // ── Delete ──
    @Override
    public void deletePolicy(Long policyId) {
        if (!policyRepository.existsById(policyId)) {
            throw new RuntimeException("Policy not found with ID: " + policyId);
        }
        policyRepository.deleteById(policyId);
    }

    // ── Reset All ──
    @Override
    public void resetPolicies() {
        policyRepository.deleteAll();
    }

    // ── Entity → DTO ──
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

        if (policy.getVehicle() != null)
            dto.setVehicleNumber(policy.getVehicle().getVehicleNumber());
        if (policy.getUser() != null)
            dto.setUserId(policy.getUser().getUserId());

        return dto;
    }

    // ── DTO → Entity ──
    private Policy mapToEntity(PolicyDTO dto) {
        Policy policy = new Policy();
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setPolicyType(dto.getPolicyType());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPremiumAmount(dto.getPremiumAmount());
        policy.setCoverageAmount(dto.getCoverageAmount());
        policy.setDeductibleAmount(dto.getDeductibleAmount());
        policy.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");

        if (dto.getVehicleNumber() != null) {
            Vehicle vehicle = vehicleRepository.findById(dto.getVehicleNumber())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + dto.getVehicleNumber()));
            policy.setVehicle(vehicle);
        }

        if (dto.getUserId() != null) {
            User customer = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + dto.getUserId()));
            policy.setUser(customer);
        }

        return policy;
    }
}