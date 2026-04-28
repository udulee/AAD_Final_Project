package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class DashboardResponse {

        private long totalPolicies;
        private long activePolicies;
        private long pendingPolicies;
        private long suspendedPolicies;
        private long expiredPolicies;
        private long rejectedPolicies;

        private long totalClaims;
        private long submittedClaims;
        private long underReviewClaims;
        private long approvedClaims;
        private long rejectedClaims;
        private long settledClaims;

        private BigDecimal totalRevenue;
        private BigDecimal totalPendingAmount;
        private BigDecimal totalPenaltyCollected;
        private long overduePayments;
        private long paidPayments;

        private long totalCustomers;
        private long activeCustomers;
        private long suspendedCustomers;
        private long totalVehicles;
        private Map<String, Long> policiesByType;

        private String generatedAt;

        public static String now() {
            return LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

