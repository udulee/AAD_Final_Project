package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service

public class AuthServiceImpl implements AuthService {
    private final UserDetailsService userDetailsService;
//    private final UserRepository userRepository;

    public AuthServiceImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
