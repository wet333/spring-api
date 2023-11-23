package online.awet.springapi.configurations.security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", length = 500, nullable = false)
    private String password;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

}
