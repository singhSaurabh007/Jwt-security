package com.example.BackendMovieReview.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MovieDto {

    private int id;
    private String name;
    private String type;
}
