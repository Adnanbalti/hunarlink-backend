package com.hunarlink.modules.auth;

import com.hunarlink.modules.auth.dto.AuthResponse;
import com.hunarlink.modules.auth.dto.SendOtpRequest;
import com.hunarlink.modules.auth.dto.VerifyOtpRequest;
import com.hunarlink.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "OTP based auth endpoints")

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/send-otp")
    public ApiResponse<String> sendOtp(@RequestBody @Valid SendOtpRequest request) {
        authService.sendOtp(request.getPhone());
        return ApiResponse.ok("OTP sent successfully", "Check console for OTP");
    }

    @PostMapping("/verify-otp")
    public ApiResponse<AuthResponse> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        AuthResponse response = authService.verifyOtp(request.getPhone(), request.getOtp());
        return ApiResponse.ok("Login successful", response);
    }
}