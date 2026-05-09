package com.hunarlink.modules.review;

import com.hunarlink.modules.booking.Booking;
import com.hunarlink.modules.booking.BookingService;
import com.hunarlink.modules.provider.Provider;
import com.hunarlink.modules.provider.ProviderRepository;
import com.hunarlink.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingService bookingService;
    private final ProviderRepository providerRepository;

    public Review createReview(UUID bookingId, Review review) {
        Booking booking = bookingService.findById(bookingId);

        if (booking.getStatus() != Booking.BookingStatus.COMPLETED) {
            throw new RuntimeException("Review can only be submitted for completed bookings");
        }

        if (reviewRepository.existsByBookingId(bookingId)) {
            throw new RuntimeException("Review already submitted for this booking");
        }

        review.setConsumer(booking.getConsumer());
        review.setProvider(booking.getProvider());
        review.setBooking(booking);

        Review saved = reviewRepository.save(review);

        Double avgRating = reviewRepository
                .findAverageRatingByProviderId(booking.getProvider().getId());
        Provider provider = booking.getProvider();
        provider.setAverageRating(avgRating != null ? avgRating : 0.0);
        providerRepository.save(provider);

        return saved;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getProviderReviews(UUID providerId) {
        return reviewRepository.findByProviderId(providerId);
    }

    public Review findById(UUID id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }
}