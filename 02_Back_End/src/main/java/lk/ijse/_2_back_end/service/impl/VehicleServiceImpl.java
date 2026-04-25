package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.VehicleRequestDTO;
import lk.ijse._2_back_end.entity.Vehicle;
import lk.ijse._2_back_end.repository.VehicleRepository;
import lk.ijse._2_back_end.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    @Override
    public void registerVehicle(VehicleRequestDTO dto) {
        if (vehicleRepository.findById(dto.getVehicleNumber()).isPresent()) {  // .isPresent() like AuthServiceImpl
            throw new RuntimeException("Vehicle with this number is already registered.");  // RuntimeException like AuthServiceImpl
        }
        vehicleRepository.save(modelMapper.map(dto, Vehicle.class));  // modelMapper like AuthServiceImpl
    }

    @Override
    public List<VehicleRequestDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map((data) -> modelMapper.map(data, VehicleRequestDTO.class))  // modelMapper like AuthServiceImpl
                .collect(Collectors.toList());
    }

    @Override
    public void updateVehicle(String vehicleNumber, VehicleRequestDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + vehicleNumber));  // RuntimeException like AuthServiceImpl

        modelMapper.map(dto, vehicle);  // map onto existing entity — preserves ID
        vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(String vehicleNumber) {
        if (!vehicleRepository.existsById(vehicleNumber)) {
            throw new RuntimeException("Vehicle not found: " + vehicleNumber);  // RuntimeException
        }
        vehicleRepository.deleteById(vehicleNumber);
    }

    @Override
    public void resetVehicles() {
        vehicleRepository.deleteAll();
    }
}