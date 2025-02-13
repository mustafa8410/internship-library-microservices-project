package com.example.webapi.service;

import com.example.webapi.entity.User;
import com.example.webapi.exceptions.security.UnauthorizedActionException;
import com.example.webapi.exceptions.user.UserNotFoundException;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.security.JwtUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException
                ("A user corresponding to the provided token could not be found."));
        return new JwtUserDetails(user.getUsername(), user.getPassword(), Collections.singletonList
                (new SimpleGrantedAuthority(user.getRole())));
    }

    public void verifyUser(User userInstance){
        if(userInstance == null)
            throw new UserNotFoundException();
        if(SecurityContextHolder.getContext().getAuthentication().getName().equals(userInstance.getUsername()))
            return;
        throw new UnauthorizedActionException();
    }


}
