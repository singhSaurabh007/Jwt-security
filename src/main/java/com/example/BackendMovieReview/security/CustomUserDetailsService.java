package com.example.BackendMovieReview.security;

import com.example.BackendMovieReview.entity.Role;
import com.example.BackendMovieReview.entity.UserInfo;
import com.example.BackendMovieReview.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("UsernaeNotFound"));
        return new User(userInfo.getUsername(),userInfo.getPassword(),mapRolesToAuthorities(userInfo.getRoles()));
    }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){

        return  roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
