package com.example.BackendMovieReview.controller;


import com.example.BackendMovieReview.Dto.AuthResponseDTO;
import com.example.BackendMovieReview.Dto.LoginDto;
import com.example.BackendMovieReview.Dto.RegisterDto;
import com.example.BackendMovieReview.entity.Role;
import com.example.BackendMovieReview.entity.UserInfo;
import com.example.BackendMovieReview.repo.RoleRepo;
import com.example.BackendMovieReview.repo.UserRepo;
import com.example.BackendMovieReview.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtGenerator jwtGenerator;

    @PostMapping("login")
    ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token),HttpStatus.OK);
    }




    @PostMapping("register")
    ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepo.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registerDto.getUsername());
        userInfo.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userInfo.setEmail(registerDto.getEmail());
        Role role = roleRepo.findByName("USER").get();
        userInfo.setRoles(Collections.singletonList(role));
        userRepo.save(userInfo);

        return  new ResponseEntity<>("user registered successfully",HttpStatus.CREATED);

    }
}
