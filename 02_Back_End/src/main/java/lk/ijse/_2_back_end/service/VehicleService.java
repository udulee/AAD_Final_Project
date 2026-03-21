package lk.ijse._2_back_end.service;


import lk.ijse._2_back_end.dto.VehicleDTO;
import lk.ijse._2_back_end.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface VehicleService {
    void save (VehicleDTO vehicleDTO);
    void update (VehicleDTO vehicleDTO);
    void delete ( String chassis);



}