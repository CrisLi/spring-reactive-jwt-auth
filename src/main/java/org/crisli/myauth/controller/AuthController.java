package org.crisli.myauth.controller;

import static java.util.Collections.singletonMap;

import javax.validation.constraints.NotEmpty;

import org.crisli.myauth.domain.User;
import org.crisli.myauth.jwt.JwtTokenManager;
import org.crisli.myauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import reactor.core.publisher.Mono;

@RestController
@RestControllerAdvice
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenManager jwtTokenManager;

    public AuthController(UserService userService) {
        this.authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userService);
    }

    @PostMapping("/auth")
    public Mono<AuthResponse> token(@RequestBody @Validated AuthRequest request) {

        Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername() + "@" + request.getOrg(),
                request.getPassword());

        return authenticationManager.authenticate(auth) //
                .map((result) -> User.class.cast(result.getPrincipal())) //
                .map((user) -> new AuthResponse(jwtTokenManager.sign(user)));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(singletonMap("message", e.getMessage()));
    }

    @Getter
    @Setter
    public static class AuthRequest {

        @NotEmpty
        private String username;

        @NotEmpty
        private String password;

        @NotEmpty
        private String org;

    }

    @Data
    public static class AuthResponse {

        @NonNull
        private String token;

    }
}
