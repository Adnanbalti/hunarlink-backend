package com.hunarlink.modules.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final StringRedisTemplate redisTemplate;
    private static final long OTP_EXPIRY_MINUTES = 5;

    public String generateAndStoreOtp(String phone) {
        String otp = String.format("%04d", new Random().nextInt(10000));
        redisTemplate.opsForValue().set(
            "otp:" + phone,
            otp,
            OTP_EXPIRY_MINUTES,
            TimeUnit.MINUTES
        );
        // Real app mein SMS bhejte — abhi console pe print
        System.out.println("OTP for " + phone + ": " + otp);
        return otp;
    }

    public boolean verifyOtp(String phone, String otp) {
        String stored = redisTemplate.opsForValue().get("otp:" + phone);
        if (stored != null && stored.equals(otp)) {
            redisTemplate.delete("otp:" + phone);
            return true;
        }
        return false;
    }
}