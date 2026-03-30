package lk.ijse._2_back_end.service;

import lk.ijse._2_back_end.dto.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(UserDTO dto);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    void updateUser(Long userId, UserDTO dto);
    void deleteUser(Long userId);
}