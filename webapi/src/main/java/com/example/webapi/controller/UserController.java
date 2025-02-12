package com.example.webapi.controller;

import com.example.webapi.entity.User;
import com.example.webapi.request.user.CreateUserRequest;
import com.example.webapi.request.user.UpdateUserRequest;
import com.example.webapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public User findUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody CreateUserRequest createUserRequest){
        return userService.createUser(createUserRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        return userService.updateUser(updateUserRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }





}
