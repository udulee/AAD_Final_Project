package lk.ijse._2_back_end.service.impl;

import lk.ijse._2_back_end.dto.UserDTO;
import lk.ijse._2_back_end.entity.User;
import lk.ijse._2_back_end.repository.UserRepository;
import lk.ijse._2_back_end.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.LazyInitializationExcludeFilter;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final LazyInitializationExcludeFilter eagerJpaMetamodelCacheCleanup;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name()) // ADMIN / CUSTOMER
                .build();
    }

    @Override
    public void createUser(UserDTO dto) {
        userRepository.save(modelMapper.map(dto,User.class));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map((data)->modelMapper.map(data,UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return modelMapper.map(user,UserDTO.class);
    }

    @Override
    public void updateUser(Long userId, UserDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.save(modelMapper.map( dto,User.class));
    }

    @Override
    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
    }
}