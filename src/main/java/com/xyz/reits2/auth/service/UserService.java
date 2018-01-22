package com.xyz.reits2.auth.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import com.xyz.reits2.auth.domain.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    public Mono<User> create(User user);

    public Flux<User> findAll();

}
