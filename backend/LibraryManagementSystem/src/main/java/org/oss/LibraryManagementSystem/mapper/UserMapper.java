package org.oss.LibraryManagementSystem.mapper;

import org.mapstruct.Mapper;
import org.oss.LibraryManagementSystem.dto.UserDto;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Mapper(componentModel = "spring")
@Component
public class UserMapper {
    public static User mapDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setContactNumber(userDto.getContactNumber());
        user.setDateOfBirth(userDto.getDateOfBirth());
        return user;
    }
}
