package lk.ijse._2_back_end.service.impl;



import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 🔴 TEMP (for testing JWT)
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("{noop}1234")
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}