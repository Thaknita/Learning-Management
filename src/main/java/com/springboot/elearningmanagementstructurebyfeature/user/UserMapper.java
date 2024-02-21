package com.springboot.elearningmanagementstructurebyfeature.user;

import com.springboot.elearningmanagementstructurebyfeature.auth.UserCreationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User mapFromuserCreationDto (UserCreationDto userCreationDto);

}

