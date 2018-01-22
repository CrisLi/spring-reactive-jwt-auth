package org.crisli.myauth.jwt;

import java.util.function.Function;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class JwtAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    public static final String BEARER = "Bearer ";

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authorization == null) {
            return Mono.empty();
        }

        if (authorization.length() <= BEARER.length()) {
            return Mono.empty();
        }

        String token = authorization.substring(BEARER.length(), authorization.length());

        return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
    }

}
