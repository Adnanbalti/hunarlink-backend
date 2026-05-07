package com.hunarlink.modules.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByConsumerId(UUID consumerId);
    List<Booking> findByProviderId(UUID providerId);
    List<Booking> findByConsumerIdAndStatus(UUID consumerId, Booking.BookingStatus status);
    List<Booking> findByProviderIdAndStatus(UUID providerId, Booking.BookingStatus status);
}