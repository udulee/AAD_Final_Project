package lk.ijse._2_back_end.repository;

import lk.ijse._2_back_end.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    boolean existsByVehicleNumber(String vehicleNumber);

    List<Vehicle> findByOwnerId(Long userId);
}