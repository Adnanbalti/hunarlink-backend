package com.hunarlink.modules.booking;

import com.hunarlink.modules.notification.Notification;
import com.hunarlink.modules.notification.NotificationService;
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
    private final NotificationService notificationService;

    public Booking createBooking(UUID consumerId, UUID providerId, Booking booking) {
        User consumer = userService.findById(consumerId);
        Provider provider = providerService.findById(providerId);

        booking.setConsumer(consumer);
        booking.setProvider(provider);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setTotalAmount(provider.getHourlyRate());

        Booking saved = bookingRepository.save(booking);

        // Notify provider
        notificationService.createNotification(
            provider.getUser(),
            "New Booking Request",
            consumer.getName() + " has requested a booking",
            Notification.NotificationType.BOOKING_CREATED
        );

        return saved;
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
        Booking updated = bookingRepository.save(booking);

        // Notifications based on status
        if (newStatus == Booking.BookingStatus.CONFIRMED) {
            notificationService.createNotification(
                booking.getConsumer(),
                "Booking Confirmed",
                "Your booking has been confirmed by the provider",
                Notification.NotificationType.BOOKING_CONFIRMED
            );
        } else if (newStatus == Booking.BookingStatus.COMPLETED) {
            notificationService.createNotification(
                booking.getConsumer(),
                "Booking Completed",
                "Your booking has been marked as completed",
                Notification.NotificationType.BOOKING_COMPLETED
            );
        } else if (newStatus == Booking.BookingStatus.CANCELLED) {
            notificationService.createNotification(
                booking.getConsumer(),
                "Booking Cancelled",
                "Your booking has been cancelled",
                Notification.NotificationType.BOOKING_CANCELLED
            );
        }

        return updated;
    }
}
