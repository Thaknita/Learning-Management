package com.springboot.elearningmanagementstructurebyfeature.user;

import com.springboot.elearningmanagementstructurebyfeature.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true, nullable = false)
    private String username;
    @Column( unique = true, nullable = false)
    private String email;
    private String password;
    private String verifyCode;
    private Boolean isVerified;
    private Boolean isDeleted;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
                )
    private Set<Role> roles;
}
