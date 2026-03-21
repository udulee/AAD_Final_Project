package lk.ijse._2_back_end.controller;


import lk.ijse._2_back_end.dto.VehicleDTO;
import lk.ijse._2_back_end.service.VehicleService;
import lk.ijse._2_back_end.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin

public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping ("/save")
    public ResponseEntity<APIResponse<String>> save( @RequestBody VehicleDTO dto) {
        vehicleService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(201, "Vehicle Registered Successfully", null));
    }

//    @GetMapping("/my/{customerId}")
//    public ResponseEntity<APIResponse<List<VehicleDTO>>> getMyVehicles(@PathVariable Integer customerId) {
//        return ResponseEntity.ok(new APIResponse<>(200, "Success", ));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Integer id) {

        return ResponseEntity.ok(new APIResponse<>(200, "Vehicle Removed", null));
    }
}