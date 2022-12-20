package com.example.BackendMovieReview.controller;

import com.example.BackendMovieReview.Dto.MovieDto;
import com.example.BackendMovieReview.Dto.ReviewDto;
import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.entity.Review;
import com.example.BackendMovieReview.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;
    @Autowired
    private ObjectMapper objectMapper;
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
    public void ReviewController_GetReviewsBymovieId_ReturnReviewDto() throws Exception {
        int movieId = 1;
        when(reviewService.getReviewsByMovieId(movieId)).thenReturn(Arrays.asList(reviewDto));

        ResultActions response = mockMvc.perform(get("/api/movie/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(reviewDto).size())));
    }

    @Test
    public void ReviewController_UpdateReview_ReturnReviewDto() throws Exception {
        int movieId = 1;
        int reviewId = 1;
        when(reviewService.updateReview(movieId, reviewId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(put("/api/movie/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }


    @Test
    public void ReviewController_CreateReview_ReturnReviewDto() throws Exception {
        int movieId = 1;
        when(reviewService.createReview(movieId, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(post("/api/movie/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_GetReviewId_ReturnReviewDto() throws Exception {
        int movieId = 1;
        int reviewId = 1;
        when(reviewService.getReviewById(reviewId, movieId)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(get("/api/movie/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
    }

    @Test
    public void ReviewController_DeleteReview_ReturnOk() throws Exception {
        int movieId = 1;
        int reviewId = 1;

        doNothing().when(reviewService).deleteReview(movieId, reviewId);

        ResultActions response = mockMvc.perform(delete("/api/movie/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }




}
