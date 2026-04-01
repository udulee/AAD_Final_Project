package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.dto.VehicleRequestDTO;
import lk.ijse._2_back_end.service.VehicleService;
import lk.ijse._2_back_end.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@CrossOrigin
@RequiredArgsConstructor  // ✅ replaces @Autowired
public class VehicleController {

    private final VehicleService vehicleService;  // ✅ final + constructor injection

    // ➕ Register Vehicle
    @PostMapping("register")
    public ResponseEntity<APIResponse> registerVehicle(@RequestBody VehicleRequestDTO dto) {
        try {
            vehicleService.registerVehicle(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse(201, "Vehicle registered successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new APIResponse(409, e.getMessage(), null));
        }
    }

    // 📋 Get All Vehicles
    @GetMapping("all")
    public ResponseEntity<APIResponse> getAllVehicles() {
        try {
            List<VehicleRequestDTO> list = vehicleService.getAllVehicles();
            return ResponseEntity.ok(new APIResponse(200, "Success", list));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }

    // ✏️ Update Vehicle
    @PutMapping("update/{vehicleNumber}")
    public ResponseEntity<APIResponse> updateVehicle(
            @PathVariable String vehicleNumber,
            @RequestBody VehicleRequestDTO dto) {
        try {
            vehicleService.updateVehicle(vehicleNumber, dto);
            return ResponseEntity.ok(new APIResponse(200, "Vehicle updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    // ❌ Delete Vehicle
    @DeleteMapping("delete/{vehicleNumber}")
    public ResponseEntity<APIResponse> deleteVehicle(@PathVariable String vehicleNumber) {
        try {
            vehicleService.deleteVehicle(vehicleNumber);
            return ResponseEntity.ok(new APIResponse(200, "Vehicle deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(404, e.getMessage(), null));
        }
    }

    // 🔄 Reset All Vehicles
    @DeleteMapping("reset")
    public ResponseEntity<APIResponse> resetVehicles() {
        try {
            vehicleService.resetVehicles();
            return ResponseEntity.ok(new APIResponse(200, "All vehicles deleted", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(500, e.getMessage(), null));
        }
    }
}