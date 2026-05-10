package com.hunarlink.modules.auth;

import com.hunarlink.modules.auth.dto.*;
import com.hunarlink.modules.provider.Provider;
import com.hunarlink.modules.provider.ProviderRepository;
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
    private final ProviderRepository providerRepository;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "hunarlink123";

    public void sendOtp(String phone) {
        otpService.generateAndStoreOtp(phone);
    }

    public CheckPhoneResponse checkPhone(String phone) {
        return userRepository.findByPhone(phone)
            .map(u -> new CheckPhoneResponse(true, u.getRole().name()))
            .orElse(new CheckPhoneResponse(false, null));
    }

    public AuthResponse registerConsumer(ConsumerRegisterRequest request) {
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setPhone(request.getPhone());
        user.setName(request.getName());
        user.setRole(User.Role.CONSUMER);
        user.setIsActive(true);
        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getPhone(), saved.getRole().name());
        return new AuthResponse(token, saved.getPhone(), saved.getRole().name(), saved.getId().toString());
    }

    public AuthResponse registerProvider(ProviderRegisterRequest request) {
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        // User banao
        User user = new User();
        user.setPhone(request.getPhone());
        user.setName(request.getName());
        user.setRole(User.Role.PROVIDER);
        user.setIsActive(true);
        User savedUser = userRepository.save(user);

        // Provider profile banao
        Provider provider = new Provider();
        provider.setUser(savedUser);
        provider.setCnic(request.getCnic());
        provider.setSkill(request.getSkill());
        provider.setCity(request.getCity());
        provider.setCountry(request.getCountry());
        provider.setYearsOfExperience(request.getYearsOfExperience());
        provider.setHourlyRate(0.0);
        provider.setIsVerified(true);
        provider.setIsActive(true);
        provider.setAverageRating(0.0);
        providerRepository.save(provider);

        String token = jwtUtil.generateToken(savedUser.getPhone(), savedUser.getRole().name());
        return new AuthResponse(token, savedUser.getPhone(), savedUser.getRole().name(), savedUser.getId().toString());
    }

    public AuthResponse verifyOtp(String phone, String otp) {
        boolean valid = otpService.verifyOtp(phone, otp);
        if (!valid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found. Please register first."));

        String token = jwtUtil.generateToken(phone, user.getRole().name());
        return new AuthResponse(token, phone, user.getRole().name(), user.getId().toString());
    }

    public AuthResponse adminLogin(AdminLoginRequest request) {
        if (!ADMIN_USERNAME.equals(request.getUsername()) ||
            !ADMIN_PASSWORD.equals(request.getPassword())) {
            throw new RuntimeException("Invalid admin credentials");
        }

        String token = jwtUtil.generateToken(ADMIN_USERNAME, "ADMIN");
        return new AuthResponse(token, ADMIN_USERNAME, "ADMIN", "admin");
    }
}