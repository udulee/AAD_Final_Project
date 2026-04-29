package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.PolicyDTO;
import lk.ijse._2_back_end.dto.PolicySaveRequestDto;

import java.util.List;

public interface PolicyService {
    void registerPolicy(PolicySaveRequestDto dto);
    List<PolicyDTO> getAllPolicies();
    PolicyDTO getPolicyById(Long id);
    void updatePolicy(Long id, PolicyDTO dto);
    void deletePolicy(Long id);
    void resetPolicies();

    Double getTotalRevenue();
    long getTotalPolicies();
    long getActivePolicies();
    long getSuspendedPolicies();
}