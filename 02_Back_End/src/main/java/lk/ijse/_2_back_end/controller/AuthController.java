package lk.ijse._2_back_end.controller;


import lk.ijse._2_back_end.dto.AuthDTO;
import lk.ijse._2_back_end.dto.UserDTO;
import lk.ijse._2_back_end.service.AuthService;
import lk.ijse._2_back_end.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("signup")
    public ResponseEntity<APIResponse> registerUser(@RequestBody UserDTO registerDTO) {
        return ResponseEntity.ok(new APIResponse
                (200,"OK",authService.register(registerDTO)));
    }
    @PostMapping("signin")
    public ResponseEntity<APIResponse> loginUser(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse(
                200,"OK",authService.authenticate(authDTO)
        ));
    }
}

