package org.crisli.myauth.jwt;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenManager jwtTokenManager;

    public JwtAuthenticationManager(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication token) {
        return Mono.just(token) //
                .publishOn(Schedulers.elastic()) //
                .map(auth -> jwtTokenManager.parse(auth.getName()))
                .map((u) -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()));
    }

}
