package mirea.loginservise.Models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles" )
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_role", nullable = false)
    private Long idRole;

    @Column(name = "role_nane",unique = true, nullable = false)
    private String roleNane;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}
