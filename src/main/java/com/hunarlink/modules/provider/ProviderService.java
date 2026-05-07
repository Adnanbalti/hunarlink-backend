package com.hunarlink.modules.provider;

import com.hunarlink.modules.user.User;
import com.hunarlink.modules.user.UserService;
import com.hunarlink.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final UserService userService;

    public Provider createProvider(UUID userId, Provider provider) {
        User user = userService.findById(userId);
        if (providerRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("Provider profile already exists for this user");
        }
        provider.setUser(user);
        return providerRepository.save(provider);
    }

    public Provider findById(UUID id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
    }

    public List<Provider> search(String skill, String city) {
        if (skill != null && city != null) {
            return providerRepository
                    .findBySkillContainingIgnoreCaseAndCityIgnoreCase(skill, city);
        } else if (skill != null) {
            return providerRepository.findBySkillContainingIgnoreCase(skill);
        } else if (city != null) {
            return providerRepository.findByCityIgnoreCase(city);
        }
        return providerRepository.findAll();
    }
}