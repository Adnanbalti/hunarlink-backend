package com.hunarlink.modules.auth;

import com.hunarlink.modules.auth.dto.AuthResponse;
import com.hunarlink.modules.user.User;
import com.hunarlink.modules.user.UserRepository;
import com.hunarlink.shared.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final OtpService otpService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public void sendOtp(String phone) {
        otpService.generateAndStoreOtp(phone);
    }

    public AuthResponse verifyOtp(String phone, String otp) {
        boolean valid = otpService.verifyOtp(phone, otp);
        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found. Please register first."));

        String token = jwtUtil.generateToken(phone, user.getRole().name());

        return new AuthResponse(token, phone, user.getRole().name());
    }
}