package com.hunarlink.modules.provider;

import com.hunarlink.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Providers", description = "Provider management endpoints")
@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping
    public ApiResponse<Provider> createProvider(
            @RequestParam UUID userId,
            @RequestBody @Valid Provider provider) {
        Provider created = providerService.createProvider(userId, provider);
        return ApiResponse.ok("Provider created successfully", created);
    }

    @GetMapping("/{id}")
    public ApiResponse<Provider> getProvider(@PathVariable UUID id) {
        Provider provider = providerService.findById(id);
        return ApiResponse.ok("Provider found", provider);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<Provider> getProviderByUserId(@PathVariable UUID userId) {
        Provider provider = providerService.findByUserId(userId);
        return ApiResponse.ok("Provider found", provider);
    }

    @GetMapping
    public ApiResponse<List<Provider>> search(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country) {
        List<Provider> providers = providerService.search(skill, city, country);
        return ApiResponse.ok("Providers fetched", providers);
    }
}