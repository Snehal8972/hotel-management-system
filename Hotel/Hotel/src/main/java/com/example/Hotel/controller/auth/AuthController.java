package com.example.Hotel.controller.auth;

import com.example.Hotel.dto.AuthenticationRequest;
import com.example.Hotel.dto.AuthenticationResponse;
import com.example.Hotel.dto.SignupRequest;
import com.example.Hotel.dto.UserDto;
import com.example.Hotel.entity.User;
import com.example.Hotel.repository.UserRepository;
import com.example.Hotel.services.auth.AuthService;
import com.example.Hotel.services.jwt.UserService;
import com.example.Hotel.utill.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

private final AuthService authService;

private final UserRepository userRepository;

private final JwtUtil jwtUtil;

private final UserService userService;

private final AuthenticationManager authenticationManager;

//@PostMapping("/signup")
//public ResponseEntity<?>signupUser(@RequestBody SignupRequest signupRequest){
//    try{
//        UserDto createdUser=authService.createUser(signupRequest);
//        return new ResponseEntity<>(createdUser, HttpStatus.OK);
//
//    }catch (EntityExistsException entityExistsException){
//        return new ResponseEntity<>("User already Exist",HttpStatus.OK);
//    }
//    catch (Exception e){
//        return new ResponseEntity<>("User not created,come again later..!",HttpStatus.BAD_REQUEST);
//    }
//}
@PostMapping("/signup")
public ResponseEntity<Map<String, Object>> signupUser(@RequestBody SignupRequest signupRequest) {
    Map<String, Object> response = new HashMap<>();

    try {
        // Try to create user
        UserDto createdUser = authService.createUser(signupRequest);

        // Return success with user id
        response.put("id", createdUser.getId());
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);

    } catch (EntityExistsException ex) {
        // User already exists
        response.put("message", "User already exists ");
        return ResponseEntity.ok(response); // Still 200 OK so frontend handles it
    } catch (Exception e) {
        // Any other errors
        response.put("message", "User not created, please try again later");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

@PostMapping("/login")
public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest ) {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Incorrect username or password");
    }
  final UserDetails userDetails =userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
    Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
    final String jwt =jwtUtil.generateToken(userDetails);
    AuthenticationResponse authenticationResponse=new AuthenticationResponse();
    if (optionalUser.isPresent()){
        authenticationResponse.setJwt(jwt);
        authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        authenticationResponse.setUserId(optionalUser.get().getId());
    }
    return authenticationResponse;
}


}
