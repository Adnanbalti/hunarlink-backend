package com.hunarlink.modules.provider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, UUID> {
    List<Provider> findBySkillContainingIgnoreCase(String skill);
    List<Provider> findByCityIgnoreCase(String city);
    List<Provider> findBySkillContainingIgnoreCaseAndCityIgnoreCase(String skill, String city);
    Optional<Provider> findByUserId(UUID userId);
}