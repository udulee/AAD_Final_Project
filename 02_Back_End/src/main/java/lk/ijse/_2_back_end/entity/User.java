package lk.ijse._2_back_end.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;  // ← id → userId (ClaimServiceImpl match කරන්න)

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role; // ← "ADMIN" / "CUSTOMER" කියලා වෙන් කරන්න

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Vehicle> vehicleList;
}