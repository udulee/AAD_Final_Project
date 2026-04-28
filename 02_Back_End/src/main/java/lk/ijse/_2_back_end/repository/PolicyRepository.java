package lk.ijse._2_back_end.repository;

import lk.ijse._2_back_end.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    boolean existsByPolicyNumber(String policyNumber);

    @Query("SELECT SUM(p.premiumAmount) FROM Policy p")
    Double sumTotalRevenue();

    long countByStatus(String active);
}