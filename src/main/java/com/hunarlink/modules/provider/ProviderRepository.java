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
    List<Provider> findByCountryIgnoreCase(String country);
    List<Provider> findBySkillContainingIgnoreCaseAndCityIgnoreCase(String skill, String city);
    List<Provider> findBySkillContainingIgnoreCaseAndCountryIgnoreCase(String skill, String country);
    List<Provider> findBySkillContainingIgnoreCaseAndCityIgnoreCaseAndCountryIgnoreCase(String skill, String city, String country);
    Optional<Provider> findByUserId(UUID userId);
}