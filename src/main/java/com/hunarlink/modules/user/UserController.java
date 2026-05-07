package com.hunarlink.modules.user;

import com.hunarlink.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("HunarLink API is running!", "v1.0.0");
    }

    @PostMapping("/users")
    public ApiResponse<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return ApiResponse.ok("User created successfully", created);
    }

    @GetMapping("/users/{id}")
    public ApiResponse<User> getUserById(@PathVariable UUID id) {
        User user = userService.findById(id);
        return ApiResponse.ok("User found", user);
    }

    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ApiResponse.ok("Users fetched", users);
    }
}