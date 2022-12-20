package com.example.BackendMovieReview.controller;

import com.example.BackendMovieReview.Dto.MovieDto;
import com.example.BackendMovieReview.Dto.MovieResponse;
import com.example.BackendMovieReview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
public class MovieController {


    @Autowired
    private MovieService movieService;



    @GetMapping("movie")
    public ResponseEntity<MovieResponse> getMovies(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(movieService.getAllMovie(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("movie/{id}")
    public ResponseEntity<MovieDto> movieDetail(@PathVariable int id) {
        return ResponseEntity.ok(movieService.getMovieById(id));

    }

    @PostMapping("movie/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }

    @PutMapping("movie/{id}/update")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto, @PathVariable("id") int pokemonId) {
        MovieDto response = movieService.updateMovie(movieDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("movie/{id}/delete")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") int pokemonId) {
        movieService.deleteMovieId(pokemonId);
        return new ResponseEntity<>("Movie delete", HttpStatus.OK);
    }

}
