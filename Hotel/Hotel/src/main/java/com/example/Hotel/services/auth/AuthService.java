package com.example.Hotel.services.auth;

import com.example.Hotel.dto.SignupRequest;
import com.example.Hotel.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
}
