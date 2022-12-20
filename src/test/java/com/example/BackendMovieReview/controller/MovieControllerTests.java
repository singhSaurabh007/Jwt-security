package com.example.BackendMovieReview.controller;

import com.example.BackendMovieReview.Dto.MovieDto;
import com.example.BackendMovieReview.Dto.MovieResponse;
import com.example.BackendMovieReview.Dto.ReviewDto;
import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.entity.Review;
import com.example.BackendMovieReview.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MovieControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

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
    public void CreateMovie_ReturnCreated() throws Exception {
        given(movieService.createMovie(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/movie/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(movieDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(movieDto.getType())));
    }

    @Test
    public void GetAllMovie_ReturnResponseDto() throws Exception {
        MovieResponse responseDto = MovieResponse.builder().pageSize(10).last(true).pageNo(1).content(Arrays.asList(movieDto)).build();
        when(movieService.getAllMovie(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void MovieDetail_ReturnPokemonDto() throws Exception {
        int movieId = 1;
        when(movieService.getMovieById(movieId)).thenReturn(movieDto);

        ResultActions response = mockMvc.perform(get("/api/movie/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(movieDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(movieDto.getType())));
    }

    @Test
    public void UpdateMovie_ReturnPokemonDto() throws Exception {
        int movieId = 1;
        when(movieService.updateMovie(movieDto, movieId)).thenReturn(movieDto);

        ResultActions response = mockMvc.perform(put("/api/movie/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(movieDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(movieDto.getType())));
    }

    @Test
    public void DeleteMovie_ReturnString() throws Exception {
        int movieId = 1;
        doNothing().when(movieService).deleteMovieId(1);

        ResultActions response = mockMvc.perform(delete("/api/movie/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
