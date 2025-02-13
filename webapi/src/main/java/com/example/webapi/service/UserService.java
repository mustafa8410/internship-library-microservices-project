package com.example.webapi.service;

import com.example.webapi.entity.User;
import com.example.webapi.exceptions.global.InvalidParameterException;
import com.example.webapi.exceptions.global.MissingArgumentException;
import com.example.webapi.exceptions.user.UserAlreadyExistsException;
import com.example.webapi.exceptions.user.UserNotFoundException;
import com.example.webapi.repository.UserRepository;
import com.example.webapi.request.user.CreateUserRequest;
import com.example.webapi.request.user.UpdateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImplementation userDetailsService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserDetailsServiceImplementation userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public User findUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userDetailsService.verifyUser(user);
        return user;
    }

    public User createUser(CreateUserRequest createUserRequest){
        if(createUserRequest.getMail().isEmpty() || createUserRequest.getName().isEmpty() ||
                createUserRequest.getPassword().isEmpty() || createUserRequest.getUsername().isEmpty())
            throw new MissingArgumentException();
        if(userRepository.existsByMail(createUserRequest.getMail()))
            throw new UserAlreadyExistsException("A user already uses this mail address.");
        if(userRepository.existsByUsername(createUserRequest.getUsername()))
            throw new UserAlreadyExistsException("A user already uses this username.");
        String role = createUserRequest.getRole();
        if(role != null && !role.equals("admin") && !role.equals("user"))
            throw new InvalidParameterException();
        else if(role == null)
            role = "user";
        User newUser = new User();
        newUser.setMail(createUserRequest.getMail());
        newUser.setName(createUserRequest.getName());
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setSurname(createUserRequest.getSurname());
        newUser.setUsername(createUserRequest.getUsername());
        newUser.setRole(role);
        return userRepository.save(newUser);
    }

    public User updateUser(UpdateUserRequest updateUserRequest){
        User user = userRepository.findById(updateUserRequest.getUserId()).orElseThrow(UserNotFoundException::new);
        userDetailsService.verifyUser(user);
        if(!user.getUsername().equals(updateUserRequest.getNewUsername()) &&
                userRepository.existsByUsername(updateUserRequest.getNewUsername()))
            throw new UserAlreadyExistsException("Another user already uses this username.");
        if(!user.getMail().equals(updateUserRequest.getNewMail()) &&
                userRepository.existsByMail(updateUserRequest.getNewMail()))
            throw new UserAlreadyExistsException("Another user already uses this username.");
        user.setUsername(updateUserRequest.getNewUsername());
        user.setSurname(updateUserRequest.getNewSurname());
        if(!passwordEncoder.matches(updateUserRequest.getNewPassword(), user.getPassword()))
            user.setPassword(passwordEncoder.encode(updateUserRequest.getNewPassword()));
        user.setName(updateUserRequest.getNewName());
        user.setMail(updateUserRequest.getNewMail());
        return userRepository.save(user);
    }

    public void deleteUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userDetailsService.verifyUser(user);
        userRepository.delete(user);
    }
}
