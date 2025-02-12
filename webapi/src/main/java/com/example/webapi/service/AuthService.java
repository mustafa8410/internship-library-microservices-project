package com.example.webapi.service;


import com.example.webapi.entity.User;
import com.example.webapi.exceptions.security.IncorrectPasswordException;
import com.example.webapi.exceptions.user.UserNotFoundException;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.request.auth.AuthLoginRequest;
import com.example.webapi.request.auth.AuthRegisterRequest;
import com.example.webapi.request.user.CreateUserRequest;
import com.example.webapi.response.AuthResponse;
import com.example.webapi.security.JwtTokenProvider;
import com.example.webapi.security.JwtUserDetails;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthService(UserDetailsServiceImplementation userDetailsServiceImplementation, AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public AuthResponse login(AuthLoginRequest authLoginRequest){
        String username = authLoginRequest.getUsername();
        JwtUserDetails userDetails = (JwtUserDetails) userDetailsServiceImplementation.loadUserByUsername(username);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authLoginRequest.getPassword(), userDetails.getAuthorities())));
        }
        catch (BadCredentialsException e){
            throw new IncorrectPasswordException();
        }
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return new AuthResponse(user.getId(), jwtTokenProvider.generateTokenWithUser(user));
    }

    public AuthResponse register(AuthRegisterRequest authRegisterRequest){
        CreateUserRequest userRequest = new CreateUserRequest(authRegisterRequest.getName(), authRegisterRequest.getSurname(), authRegisterRequest.getMail(), authRegisterRequest.getUsername(), authRegisterRequest.getPassword(), "user");
        User newUser = userService.createUser(userRequest);
        return new AuthResponse(newUser.getId(), jwtTokenProvider.generateTokenWithUser(newUser));
    }
}
