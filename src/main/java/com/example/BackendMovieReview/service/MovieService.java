package com.example.BackendMovieReview.service;


import com.example.BackendMovieReview.Dto.MovieDto;
import com.example.BackendMovieReview.Dto.MovieResponse;
import com.example.BackendMovieReview.Exception.MovieNotFoundException;
import com.example.BackendMovieReview.entity.Movie;
import com.example.BackendMovieReview.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {



    @Autowired
    MovieRepo movieRepository;




    public MovieDto createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());

        Movie newMovie = movieRepository.save(movie);

        MovieDto pokemonResponse = new MovieDto();
        pokemonResponse.setId(newMovie.getId());
        pokemonResponse.setName(newMovie.getName());
        pokemonResponse.setType(newMovie.getType());
        return pokemonResponse;
    }


    public MovieResponse getAllMovie(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Movie> pokemons = movieRepository.findAll(pageable);
        List<Movie> listOfMovie = pokemons.getContent();
        List<MovieDto> content = listOfMovie.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setContent(content);
        movieResponse.setPageNo(pokemons.getNumber());
        movieResponse.setPageSize(pokemons.getSize());
        movieResponse.setTotalElements(pokemons.getTotalElements());
        movieResponse.setTotalPages(pokemons.getTotalPages());
        movieResponse.setLast(pokemons.isLast());

        return movieResponse;
    }


    public MovieDto getMovieById(int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be found"));
        return mapToDto(movie);
    }


    public MovieDto updateMovie(MovieDto movieDto, int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be updated"));

        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());

        Movie updatedMovie = movieRepository.save(movie);
        return mapToDto(updatedMovie);
    }


    public void deleteMovieId(int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie could not be delete"));
        movieRepository.delete(movie);
    }

    private MovieDto mapToDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setName(movie.getName());
        movieDto.setType(movie.getType());
        return movieDto;
    }

    private Movie mapToEntity(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setType(movieDto.getType());
        return movie;
    }
}
