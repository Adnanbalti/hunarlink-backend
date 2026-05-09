package com.hunarlink.modules.review;

import com.hunarlink.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reviews", description = "Review management endpoints")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<Review> createReview(
            @RequestParam UUID bookingId,
            @RequestBody @Valid Review review) {
        Review created = reviewService.createReview(bookingId, review);
        return ApiResponse.ok("Review submitted successfully", created);
    }

    @GetMapping
    public ApiResponse<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ApiResponse.ok("All reviews fetched", reviews);
    }

    @GetMapping("/provider/{providerId}")
    public ApiResponse<List<Review>> getProviderReviews(@PathVariable UUID providerId) {
        List<Review> reviews = reviewService.getProviderReviews(providerId);
        return ApiResponse.ok("Reviews fetched", reviews);
    }
}