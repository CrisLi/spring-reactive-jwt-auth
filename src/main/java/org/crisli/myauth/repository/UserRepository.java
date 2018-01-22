package org.crisli.myauth.repository;

import org.crisli.myauth.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    public Mono<User> findByUsernameAndOrg(String username, String org);

}
