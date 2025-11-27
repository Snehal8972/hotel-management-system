//package com.example.Hotel.services.jwt;
//
//import com.example.Hotel.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserServicelmpl implements UserService{
//
//    private final UserRepository userRepository;
//
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return userRepository.findFirstByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
//            }
//        };
//    }
//}
package com.example.Hotel.services.jwt;

import com.example.Hotel.entity.User;
import com.example.Hotel.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServicelmpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    // This method is called by Spring Security during authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    // This method is used in your WebSecurityConfiguration
    @Override
    public UserDetailsService userDetailsService() {
        return this; // Now it correctly returns a UserDetailsService
    }
}
