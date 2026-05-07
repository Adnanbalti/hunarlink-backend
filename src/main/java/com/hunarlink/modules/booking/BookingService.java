package com.hunarlink.modules.booking;

import com.hunarlink.modules.provider.Provider;
import com.hunarlink.modules.provider.ProviderService;
import com.hunarlink.modules.user.User;
import com.hunarlink.modules.user.UserService;
import com.hunarlink.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ProviderService providerService;

    public Booking createBooking(UUID consumerId, UUID providerId, Booking booking) {
        User consumer = userService.findById(consumerId);
        Provider provider = providerService.findById(providerId);

        booking.setConsumer(consumer);
        booking.setProvider(provider);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setTotalAmount(provider.getHourlyRate());

        return bookingRepository.save(booking);
    }

    public Booking findById(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }

    public List<Booking> getMyBookings(UUID userId) {
        return bookingRepository.findByConsumerId(userId);
    }

    public List<Booking> getProviderBookings(UUID providerId) {
        return bookingRepository.findByProviderId(providerId);
    }

    public Booking updateStatus(UUID bookingId, Booking.BookingStatus newStatus) {
        Booking booking = findById(bookingId);

        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a cancelled booking");
        }
        if (booking.getStatus() == Booking.BookingStatus.COMPLETED) {
            throw new RuntimeException("Cannot update a completed booking");
        }

        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }
}