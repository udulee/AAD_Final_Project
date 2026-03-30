package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.VehicleRequestDTO;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void registerVehicle(VehicleRequestDTO dto) throws Exception {
        Optional<Vehicle> existing = vehicleRepository.findById(dto.getVehicleNumber());
        if (existing.isPresent()) {
            throw new Exception("Vehicle with this number is already registered.");
        }

        Vehicle vehicle = mapToEntity(dto);
        vehicleRepository.save(vehicle);
    }


    @Override
    public List<VehicleRequestDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void updateVehicle(String vehicleNumber, VehicleRequestDTO dto) throws Exception {
        Vehicle vehicle = vehicleRepository.findById(vehicleNumber)
                .orElseThrow(() -> new Exception("Vehicle not found: " + vehicleNumber));

        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setManufacturedYear(dto.getManufacturedYear());
        vehicle.setMarketValue(dto.getMarketValue());
        vehicle.setUsageType(dto.getUsageType());

        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(String vehicleNumber) throws Exception {
        if (!vehicleRepository.existsById(vehicleNumber)) {
            throw new Exception("Vehicle not found: " + vehicleNumber);
        }
        vehicleRepository.deleteById(vehicleNumber);
    }

    @Override
    public void resetVehicles() {
        vehicleRepository.deleteAll();
    }

    private Vehicle mapToEntity(VehicleRequestDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(dto.getVehicleNumber());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setManufacturedYear(dto.getManufacturedYear());
        vehicle.setMarketValue(dto.getMarketValue());
        vehicle.setUsageType(dto.getUsageType());
        return vehicle;
    }

    private VehicleRequestDTO mapToDTO(Vehicle vehicle) {
        VehicleRequestDTO dto = new VehicleRequestDTO();
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setManufacturedYear(vehicle.getManufacturedYear());
        dto.setMarketValue(vehicle.getMarketValue());
        dto.setUsageType(vehicle.getUsageType());
        return dto;
    }
}