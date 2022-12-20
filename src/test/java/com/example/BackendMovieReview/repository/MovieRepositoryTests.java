package com.example.BackendMovieReview.repository;

import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.repo.MovieRepo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest

public class MovieRepositoryTests {

    @Autowired
    private MovieRepo movieRepository;

    @Test
    public void movieRepository_SaveAll_ReturnSavedmovie() {

        //Arrange
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();

        //Act
        Movie savedMovie = movieRepository.save(movie);

        //Assert
        Assertions.assertThat(savedMovie).isNotNull();
        Assertions.assertThat(savedMovie.getId()).isGreaterThan(0);
    }

    @Test
    public void movieRepository_GetAll_ReturnMoreThenOnemovie() {
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();
        Movie movie2 = Movie.builder()
                .name("pikachu")
                .type("electric").build();

        movieRepository.save(movie);
        movieRepository.save(movie2);

        List<Movie> movieList = movieRepository.findAll();

        Assertions.assertThat(movieList).isNotNull();
        Assertions.assertThat(movieList.size()).isEqualTo(2);
    }

    @Test
    public void movieRepository_FindById_Returnmovie() {
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();

        movieRepository.save(movie);

        Movie movieList = movieRepository.findById(movie.getId()).get();

        Assertions.assertThat(movieList).isNotNull();
    }

    @Test
    public void movieRepository_FindByType_ReturnmovieNotNull() {
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();

        movieRepository.save(movie);

        Movie movieList = movieRepository.findByType(movie.getType()).get();

        Assertions.assertThat(movieList).isNotNull();
    }

    @Test
    public void movieRepository_Updatemovie_ReturnmovieNotNull() {
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();

        movieRepository.save(movie);

        Movie movieSave = movieRepository.findById(movie.getId()).get();
        movieSave.setType("Electric");
        movieSave.setName("Raichu");

        Movie updatedMovie = movieRepository.save(movieSave);

        Assertions.assertThat(updatedMovie.getName()).isNotNull();
        Assertions.assertThat(updatedMovie.getType()).isNotNull();
    }

    @Test
    public void movieRepository_movieDelete_ReturnmovieIsEmpty() {
        Movie movie = Movie.builder()
                .name("pikachu")
                .type("electric").build();

        movieRepository.save(movie);

        movieRepository.deleteById(movie.getId());
        Optional<Movie> movieReturn = movieRepository.findById(movie.getId());

        Assertions.assertThat(movieReturn).isEmpty();
    }


}
