package com.example.BackendMovieReview.controller;

import com.example.BackendMovieReview.Dto.ReviewDto;
import com.example.BackendMovieReview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/")
public class ReviewController {


    @Autowired
    private ReviewService reviewService;



    @PostMapping("/movie/{movieId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "movieId") int movieId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(movieId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/movie/{pokemonId}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonId") int pokemonId) {
        return reviewService.getReviewsByMovieId(pokemonId);
    }

    @GetMapping("/movie/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/movie/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(pokemonId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/movie/{pokemonId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(pokemonId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
