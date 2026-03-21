package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.VehicleDTO;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;



    @Override
    public void save(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle(
                vehicleDTO.getChassis(),
                vehicleDTO.getVehicleNo(),
                vehicleDTO.getVehicleType(),
                vehicleDTO.getModel(),
                vehicleDTO.getFuelType()
        );

        vehicleRepository.save(vehicle);
    }

    @Override
    public void update(VehicleDTO vehicleDTO) {

    }

    @Override
    public void delete(String chassis) {

    }
}
