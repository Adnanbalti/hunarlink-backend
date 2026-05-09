package com.hunarlink.modules.booking;

import com.hunarlink.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bookings", description = "Booking management endpoints")

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ApiResponse<Booking> createBooking(
            @RequestParam UUID consumerId,
            @RequestParam UUID providerId,
            @RequestBody @Valid Booking booking) {
        Booking created = bookingService.createBooking(consumerId, providerId, booking);
        return ApiResponse.ok("Booking created successfully", created);
    }

    @GetMapping("/{id}")
    public ApiResponse<Booking> getBooking(@PathVariable UUID id) {
        Booking booking = bookingService.findById(id);
        return ApiResponse.ok("Booking found", booking);
    }

    @GetMapping("/my")
    public ApiResponse<List<Booking>> getMyBookings(@RequestParam UUID consumerId) {
        List<Booking> bookings = bookingService.getMyBookings(consumerId);
        return ApiResponse.ok("Bookings fetched", bookings);
    }

    @GetMapping("/provider")
    public ApiResponse<List<Booking>> getProviderBookings(@RequestParam UUID providerId) {
        List<Booking> bookings = bookingService.getProviderBookings(providerId);
        return ApiResponse.ok("Provider bookings fetched", bookings);
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Booking> updateStatus(
            @PathVariable UUID id,
            @RequestParam Booking.BookingStatus status) {
        Booking updated = bookingService.updateStatus(id, status);
        return ApiResponse.ok("Booking status updated", updated);
    }
}