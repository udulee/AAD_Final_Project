package lk.ijse._2_back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private Long userId;
    private String username;
    private String email;
    private Integer role;
}