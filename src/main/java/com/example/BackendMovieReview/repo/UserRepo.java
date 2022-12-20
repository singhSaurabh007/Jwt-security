package com.example.BackendMovieReview.repo;

import com.example.BackendMovieReview.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByUsername(String username);
    Boolean existsByUsername(String username);
}
