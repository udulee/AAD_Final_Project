//package lk.ijse._2_back_end.service;
//
//import lk.ijse._2_back_end.dto.PolicyRequestDTO;
//import lk.ijse._2_back_end.entity.InsurancePolicy;
//import lk.ijse._2_back_end.entity.Payment;
//import lk.ijse._2_back_end.entity.Vehicle;
//import lk.ijse._2_back_end.repository.PaymentRepository;
//import lk.ijse._2_back_end.repository.PolicyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//
//@Service
//public class PolicyService {
////    @Autowired
////    private InsurancePolicyRepository policyRepository;
////    @Autowired
////    private PaymentRepository paymentRepository;
//
//    public InsurancePolicy createPolicy(PolicyRequestDTO dto, Vehicle vehicle) {
//        InsurancePolicy policy = new InsurancePolicy();
//        policy.setVehicle(vehicle);
//
//        // 1. Basic Premium Calculation (Simple Example)
//        double basicPremium = (vehicle.getVehicleType().equals("CAR")) ? 50000.0 : 20000.0;
//
//        // 2. NCB Logic (PDF details anuwa)
//        // Danata nikanma 10% discount ekak dammu
//        double discount = basicPremium * 0.10;
//        policy.setPremiumAmount(basicPremium - discount);
//        policy.setNcbDiscount(10.0);
//        policy.setStatus("PENDING"); // Admin approve karanna ona
//
//        InsurancePolicy savedPolicy = PolicyRepository.save(policy);
//
//        // 3. Installment Logic
//        generateInstallments(savedPolicy, dto.getInstallmentPlan());
//
//        return savedPolicy;
//    }
//
//    private void generateInstallments(InsurancePolicy policy, int months) {
//        double installmentAmt = policy.getPremiumAmount() / months;
//        for (int i = 1; i <= months; i++) {
//            Payment p = new Payment();
//            p.setAmount(installmentAmt);
//            p.setDueDate(LocalDate.now().plusMonths(i));
//            p.setPaymentStatus("PENDING");
//            p.setPolicy(policy);
//            PaymentRepository.save(p);
//        }
//    }
//}
