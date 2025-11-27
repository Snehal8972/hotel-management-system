package com.example.Hotel.dto;

import com.example.Hotel.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private  String email;
    private String name;
    private UserRole userRole;
}
