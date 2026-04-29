package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.AuthDTO;
import lk.ijse._2_back_end.dto.AuthResponseDTO;
import lk.ijse._2_back_end.dto.UserDTO;
import lk.ijse._2_back_end.entity.Role;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.service.AuthService;
import lk.ijse._2_back_end.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    public AuthResponseDTO authenticate(AuthDTO authDTO){
        User user=userRepository.findByUsername(authDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException(authDTO.getUsername()));

        if (!passwordEncoder.matches(authDTO.getPassword(),user.getPassword())){
            throw new BadCredentialsException(authDTO.getUsername());
        }

        String token=jwtUtil.generateToken(authDTO.getUsername());
        int roleValue = user.getRole() == Role.ADMIN ? 0 : 1;
        
        return new AuthResponseDTO(
                token,
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                roleValue
        );
    }
    public String register(UserDTO registerDTO){
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()){
            throw new RuntimeException("Username is already in use");
        }
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        if (registerDTO.getRole() != null) {
            Role role = registerDTO.getRole() == 0 ? Role.ADMIN : Role.USER;
            registerDTO.setRoleValue(role);
        }

        userRepository.save(modelMapper.map(registerDTO,User.class));
        return "User registered successfully";
    }
}
