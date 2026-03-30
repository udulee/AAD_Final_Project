package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.VehicleRequestDTO;
import java.util.List;

public interface VehicleService {
    void registerVehicle(VehicleRequestDTO vehicleRequestDTO) throws Exception;
    List<VehicleRequestDTO> getAllVehicles();
    void updateVehicle(String vehicleNumber, VehicleRequestDTO dto) throws Exception;
    void deleteVehicle(String vehicleNumber) throws Exception;
    void resetVehicles();
}