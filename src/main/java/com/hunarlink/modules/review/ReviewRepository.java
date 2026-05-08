package com.hunarlink.modules.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByProviderId(UUID providerId);
    Optional<Review> findByBookingId(UUID bookingId);
    boolean existsByBookingId(UUID bookingId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.provider.id = :providerId")
    Double findAverageRatingByProviderId(UUID providerId);
}