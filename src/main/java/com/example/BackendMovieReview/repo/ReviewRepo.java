package com.example.BackendMovieReview.repo;

import com.example.BackendMovieReview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Integer> {

    List<Review> findByMovieId(int movieId);
}
