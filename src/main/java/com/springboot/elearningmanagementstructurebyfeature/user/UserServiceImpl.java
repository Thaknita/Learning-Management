package com.springboot.elearningmanagementstructurebyfeature.user;


import com.springboot.elearningmanagementstructurebyfeature.auth.UserCreationDto;
import com.springboot.elearningmanagementstructurebyfeature.role.Role;
import com.springboot.elearningmanagementstructurebyfeature.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Transactional
    @Override
    public void createUser(UserCreationDto userCreationDto) {

        User user = userMapper.mapFromuserCreationDto(userCreationDto);
        user.setIsDeleted(false);
        user.setIsVerified(false);
        user.setPassword(passwordEncoder.encode(userCreationDto.password()));

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already existed");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already Taken");
        }
        Set<Role> roles = new HashSet<>();
        userCreationDto.roleName().forEach(name -> {
            Role role = roleRepository.findByName(name).orElseThrow(
                    ()->  new ResponseStatusException(HttpStatus.NOT_FOUND, "role invalid!")
            );
            roles.add(role);
        });

        user.setRoles(roles);
        userRepository.save(user);
    }


}
