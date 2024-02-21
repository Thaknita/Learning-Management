package com.springboot.elearningmanagementstructurebyfeature.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "authorities", qualifiedByName = "toAuthorities")
    RoleDto toRoleDto(Role role);

    List<RoleDto> toRoleListDto(List<Role> roles);

    @Named("toAuthorities")
    default List<String> toAuthorities(List<Authority> authorities) {
        return authorities
                .stream()
                .map(Authority::getName)
                .toList();
    }

}
