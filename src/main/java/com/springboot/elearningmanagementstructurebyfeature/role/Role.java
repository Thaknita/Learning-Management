package com.springboot.elearningmanagementstructurebyfeature.role;
import com.springboot.elearningmanagementstructurebyfeature.auth.Authority;
import com.springboot.elearningmanagementstructurebyfeature.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany (mappedBy = "roles")
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    //@OneToMany(mappedBy = "role")
    //private  List<RoleAuthority> roleAuthorities;
}
