package com.vmo.freshermanagement.intern.controller;

import com.vmo.freshermanagement.intern.entity.User;
import com.vmo.freshermanagement.intern.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "All users list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    @Operation(summary = "Add user account")
    public User addUser(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        return userService.addUser(username, password);
    }

    @PutMapping("/users/{user_id}")
    @Operation(summary = "Update user account")
    public User updateUser(@PathVariable("user_id") int userId,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password) {
        return userService.updateUser(userId, username, password);
    }

    @PutMapping("/users/{username}/lock-user")
    @Operation(summary = "Lock user account")
    public User lockUser(@PathVariable("username") String username) {
        return userService.lockUser(username);
    }

    @PutMapping("/users/{username}/unlock-user")
    @Operation(summary = "Unlock user account")
    public User unlockUser(@PathVariable("username") String username) {
        return userService.unlockUser(username);
    }
}
