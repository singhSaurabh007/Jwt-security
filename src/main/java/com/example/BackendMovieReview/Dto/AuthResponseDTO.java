package com.example.BackendMovieReview.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String accessToken;

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    private String tokenType="Bearer ";


}
