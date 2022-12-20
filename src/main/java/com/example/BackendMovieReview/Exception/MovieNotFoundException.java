package com.example.BackendMovieReview.Exception;

public class MovieNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public MovieNotFoundException(String message) {
        super(message);
    }
}
