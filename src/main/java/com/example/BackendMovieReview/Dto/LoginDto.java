package com.example.BackendMovieReview.Dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {

    String username;
    String password;
}
