package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.AuthDTO;
import lk.ijse._2_back_end.dto.AuthResponseDTO;
import lk.ijse._2_back_end.dto.UserDTO;

public interface AuthService {

    AuthResponseDTO authenticate(AuthDTO authDTO);

    String register(UserDTO registerDTO);
}