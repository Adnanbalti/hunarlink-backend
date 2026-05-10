package com.hunarlink.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckPhoneResponse {
    private boolean exists;
    private String role;
}