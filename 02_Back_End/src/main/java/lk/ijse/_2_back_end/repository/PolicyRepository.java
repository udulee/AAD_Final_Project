package lk.ijse._2_back_end.repository;

import lk.ijse._2_back_end.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    boolean existsByPolicyNumber(String policyNumber);
    List<Policy> findByUser_UserId(Long userId);
    List<Policy> findByVehicle_VehicleNumber(String vehicleNumber);
}