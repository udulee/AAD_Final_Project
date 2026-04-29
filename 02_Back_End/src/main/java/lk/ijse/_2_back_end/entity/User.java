package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lk.ijse._2_back_end.config.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // id /userId

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String fullName;
    private String email;
    @Convert(converter = RoleConverter.class)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Vehicle> vehicleList;
}