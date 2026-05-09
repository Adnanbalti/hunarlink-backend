package com.hunarlink.modules.auth.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String username;
    private String password;
}