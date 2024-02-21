package com.springboot.elearningmanagementstructurebyfeature.security;

import com.springboot.elearningmanagementstructurebyfeature.user.User;
import com.springboot.elearningmanagementstructurebyfeature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmailAndIsDeletedAndIsVerified(username, false, true)
                .orElseThrow(()-> new UsernameNotFoundException("Incorrect Password or Email"));

        CustomUserDetails customUserDetail = new CustomUserDetails();
        customUserDetail.setUser(user);

        return customUserDetail;
    }
}
