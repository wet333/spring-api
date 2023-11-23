package online.awet.springapi.configurations.security.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "authority"})
})
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;
    @Column(name = "authority", nullable = false)
    private String authority;

}
