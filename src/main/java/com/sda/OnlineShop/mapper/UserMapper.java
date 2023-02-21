package com.sda.OnlineShop.mapper;

import com.sda.OnlineShop.dto.RegistrationDto;
import com.sda.OnlineShop.entities.User;
import com.sda.OnlineShop.entities.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User map(RegistrationDto registrationDto) {
        User user = new User();
        user.setFullName(registrationDto.getFullName());
        user.setEmailAddress(registrationDto.getEmailAddress());
        user.setPassword(registrationDto.getPassword());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setAddress(registrationDto.getAddress());
        user.setUserRole(UserRole.valueOf(registrationDto.getUserRole()));
        return user;
    }

}