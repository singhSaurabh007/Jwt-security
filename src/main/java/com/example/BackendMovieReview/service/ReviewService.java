package com.example.BackendMovieReview.service;

import com.example.BackendMovieReview.Dto.ReviewDto;
import com.example.BackendMovieReview.Exception.MovieNotFoundException;
import com.example.BackendMovieReview.Exception.ReviewNotFoundException;
import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.entity.Review;
import com.example.BackendMovieReview.repo.MovieRepo;
import com.example.BackendMovieReview.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepo reviewRepository;
    @Autowired
    private MovieRepo movieRepository;




    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Movie movie = movieRepository.findById(pokemonId).orElseThrow(() -> new MovieNotFoundException("Movie with associated review not found"));

        review.setMovie(movie);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }


    public List<ReviewDto> getReviewsByMovieId(int id) {
        List<Review> reviews = reviewRepository.findByMovieId(id);

        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }


    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Movie movie = movieRepository.findById(pokemonId).orElseThrow(() -> new MovieNotFoundException("Movie with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate movie not found"));

        if(review.getMovie().getId() != movie.getId()) {
            throw new ReviewNotFoundException("This review does not belond to a movie");
        }

        return mapToDto(review);
    }


    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        Movie movie = movieRepository.findById(pokemonId).orElseThrow(() -> new MovieNotFoundException("Movie with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate movie not found"));

        if(review.getMovie().getId() != movie.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a movie");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);
    }


    public void deleteReview(int pokemonId, int reviewId) {
        Movie movie = movieRepository.findById(pokemonId).orElseThrow(() -> new MovieNotFoundException("Movie with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate movie not found"));

        if(review.getMovie().getId() != movie.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a movie");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
