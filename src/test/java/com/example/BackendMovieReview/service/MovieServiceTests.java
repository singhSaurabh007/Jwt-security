package com.example.BackendMovieReview.service;


import com.example.BackendMovieReview.Dto.MovieDto;
import com.example.BackendMovieReview.Dto.MovieResponse;
import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.repo.MovieRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

    @Mock
    private MovieRepo movieRepository;

    @InjectMocks
    private MovieService service;

    @Test
    public void movieService_Createmovie_ReturnsmovieDto() {
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();
        MovieDto movieDto = MovieDto.builder().name("pickachu").type("electric").build();

        when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);

        MovieDto savedmovie = service.createMovie(movieDto);

        Assertions.assertThat(savedmovie).isNotNull();
    }

    @Test
    public void movieService_GetAllmovie_ReturnsResponseDto() {
        Page<Movie> movies = Mockito.mock(Page.class);

        when(movieRepository.findAll(Mockito.any(Pageable.class))).thenReturn(movies);

        MovieResponse savemovie = service.getAllMovie(1,10);

        Assertions.assertThat(savemovie).isNotNull();
    }
    @Test
    public void movieService_FindById_ReturnmovieDto() {
        int movieId = 1;
        Movie movie = Movie.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
        when(movieRepository.findById(movieId)).thenReturn(Optional.ofNullable(movie));

        MovieDto movieReturn = service.getMovieById(movieId);

        Assertions.assertThat(movieReturn).isNotNull();
    }

    @Test
    public void movieService_Updatemovie_ReturnmovieDto() {
        int movieId = 1;
        Movie movie = Movie.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
        MovieDto movieDto = MovieDto.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

        when(movieRepository.findById(movieId)).thenReturn(Optional.ofNullable(movie));
        when(movieRepository.save(movie)).thenReturn(movie);

        MovieDto updateReturn = service.updateMovie(movieDto, movieId);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void movieService_DeletemovieById_ReturnVoid() {
        int movieId = 1;
        Movie movie = Movie.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

        when(movieRepository.findById(movieId)).thenReturn(Optional.ofNullable(movie));
        doNothing().when(movieRepository).delete(movie);

        assertAll(() -> service.deleteMovieId(movieId));
    }
}
