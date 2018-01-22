package com.xyz.reits2.auth.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.xyz.reits2.auth.domain.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    public Mono<User> findByUsernameAndOrg(String username, String org);

}
