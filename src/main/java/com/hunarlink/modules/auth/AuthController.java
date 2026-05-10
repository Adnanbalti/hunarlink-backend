package com.hunarlink.modules.auth;

import com.hunarlink.modules.auth.dto.*;
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

    @GetMapping("/check-phone")
    public ApiResponse<CheckPhoneResponse> checkPhone(@RequestParam String phone) {
        CheckPhoneResponse response = authService.checkPhone(phone);
        return ApiResponse.ok("Phone checked", response);
    }

    @PostMapping("/register/consumer")
    public ApiResponse<AuthResponse> registerConsumer(
            @RequestBody @Valid ConsumerRegisterRequest request) {
        AuthResponse response = authService.registerConsumer(request);
        return ApiResponse.ok("Consumer registered successfully", response);
    }

    @PostMapping("/register/provider")
    public ApiResponse<AuthResponse> registerProvider(
            @RequestBody @Valid ProviderRegisterRequest request) {
        AuthResponse response = authService.registerProvider(request);
        return ApiResponse.ok("Provider registered successfully", response);
    }

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

    @PostMapping("/admin/login")
    public ApiResponse<AuthResponse> adminLogin(@RequestBody AdminLoginRequest request) {
        AuthResponse response = authService.adminLogin(request);
        return ApiResponse.ok("Admin login successful", response);
    }
}