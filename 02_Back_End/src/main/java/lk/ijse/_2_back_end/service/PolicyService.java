package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.PolicyDTO;

import java.util.List;

public interface PolicyService {
    void registerPolicy(PolicyDTO dto);
    List getAllPolicies();
    void getPolicyById();
    void updatePolicy(Long id,PolicyDTO dto);
    void deletePolicy(Long id);

    void resetPolicies();
}