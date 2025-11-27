package com.example.Hotel.dto;

import com.example.Hotel.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

private String jwt;

private Long userId;

private UserRole userRole;


}
