package com.example.BackendMovieReview.service;


import com.example.BackendMovieReview.Dto.MovieDto;
import com.example.BackendMovieReview.Dto.ReviewDto;
import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.entity.Review;
import com.example.BackendMovieReview.repo.MovieRepo;
import com.example.BackendMovieReview.repo.ReviewRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepo reviewRepository;
    @Mock
    private MovieRepo movieRepository;
    @InjectMocks
    private ReviewService reviewService;

    private Movie movie;
    private Review review;
    private ReviewDto reviewDto;
    private MovieDto movieDto;

    @BeforeEach
    public void init() {
        movie = Movie.builder().name("pikachu").type("electric").build();
        movieDto = MovieDto.builder().name("pickachu").type("electric").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void ReviewService_CreateReview_ReturnsReviewDto() {
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(movie.getId(), reviewDto);

        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewsBymovieId_ReturnReviewDto() {
        int reviewId = 1;
        when(reviewRepository.findByMovieId(reviewId)).thenReturn(Arrays.asList(review));

        List<ReviewDto> pokemonReturn = reviewService.getReviewsByMovieId(reviewId);

        Assertions.assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewById_ReturnReviewDto() {
        int reviewId = 1;
        int movieId = 1;

        review.setMovie(movie);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewDto reviewReturn = reviewService.getReviewById(reviewId, movieId);

        Assertions.assertThat(reviewReturn).isNotNull();
        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void ReviewService_UpdatePokemon_ReturnReviewDto() {
        int movieId = 1;
        int reviewId = 1;
        movie.setReviews(Arrays.asList(review));
        review.setMovie(movie);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(review)).thenReturn(review);

        ReviewDto updateReturn = reviewService.updateReview(movieId, reviewId, reviewDto);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void ReviewService_DeletePokemonById_ReturnVoid() {
        int movieId = 1;
        int reviewId = 1;

        movie.setReviews(Arrays.asList(review));
        review.setMovie(movie);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertAll(() -> reviewService.deleteReview(movieId, reviewId));
    }


}
