package com.springboot.elearningmanagementstructurebyfeature.auth;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
   UserCreationDto fromUserRegistrationDto (UserRegisterDto userRegisterDto);

}
