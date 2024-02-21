package com.springboot.elearningmanagementstructurebyfeature.role;

import com.springboot.elearningmanagementstructurebyfeature.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private List<Role> roles;

    //@OneToMany(mappedBy = "authority")
    //private List<RoleAuthority> roleAuthorities;
}
