package com.springboot.elearningmanagementstructurebyfeature.user;

import com.springboot.elearningmanagementstructurebyfeature.auth.UserCreationDto;

public interface UserService {

    void createUser(UserCreationDto userCreationDto);

}
