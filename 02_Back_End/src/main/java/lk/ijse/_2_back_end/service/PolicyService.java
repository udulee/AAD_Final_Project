package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.PolicyDTO;

import java.util.List;

public interface PolicyService {
    void registerPolicy(PolicyDTO dto);
    List<PolicyDTO> getAllPolicies();
    PolicyDTO getPolicyById(Long policyId);
    void updatePolicy(Long policyId, PolicyDTO dto);
    void deletePolicy(Long policyId);
    void resetPolicies();
}