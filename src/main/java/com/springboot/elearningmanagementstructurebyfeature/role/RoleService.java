package com.springboot.elearningmanagementstructurebyfeature.role;

import java.util.List;


public interface RoleService {
    List<RoleDto> findAll();

    RoleDto findByName(String name);
}
