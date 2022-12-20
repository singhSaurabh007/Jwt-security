package com.example.BackendMovieReview.repo;

import com.example.BackendMovieReview.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie,Integer> {

    Optional<Movie> findByType(String type);
}
