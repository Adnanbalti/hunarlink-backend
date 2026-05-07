package com.hunarlink.modules.user;

import com.hunarlink.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.ok("HunarLink API is running!", "v1.0.0");
    }
}