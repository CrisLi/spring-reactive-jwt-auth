package org.crisli.myauth.service;

import org.crisli.myauth.domain.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    public Mono<User> create(User user);

    public Flux<User> findAll();

}
