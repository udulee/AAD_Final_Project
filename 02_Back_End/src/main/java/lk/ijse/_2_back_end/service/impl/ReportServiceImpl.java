package lk.ijse._2_back_end.service.impl;


import lk.ijse._2_back_end.dto.ReportDTO;
import lk.ijse._2_back_end.repository.ClaimRepository;
import lk.ijse._2_back_end.repository.PolicyRepository;
import lk.ijse._2_back_end.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;

    @Override
    public ReportDTO generateReport() {
        long   total     = policyRepository.count();
        long   active    = policyRepository.countByStatus("ACTIVE");
        long   suspended = policyRepository.countByStatus("SUSPENDED");
        Double revenue   = policyRepository.sumTotalRevenue();
        long   pending   = claimRepository.countByClaimStatus("PENDING");
        long   approved  = claimRepository.countByClaimStatus("APPROVED");

        return new ReportDTO(
                total,
                active,
                suspended,
                revenue != null ? revenue : 0.0,
                pending,
                approved
        );
    }
}