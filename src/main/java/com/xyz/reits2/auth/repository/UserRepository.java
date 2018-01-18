package com.xyz.reits2.auth.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.xyz.reits2.auth.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
