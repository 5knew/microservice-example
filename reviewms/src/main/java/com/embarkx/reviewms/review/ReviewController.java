package com.embarkx.reviewms.review;

import com.embarkx.reviewms.review.messages.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review review,
                                            @RequestParam Long companyId) {
        boolean isReviewSaved = reviewService.addReview(companyId, review);
        if (isReviewSaved) {
            reviewMessageProducer.sendMessage(review, companyId);
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(
            @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Review review) {
        boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
        if (isReviewUpdated)
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if (isReviewDeleted)
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average()
                .orElse(0.0);
    }

}
