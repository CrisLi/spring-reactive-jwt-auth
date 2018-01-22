package com.xyz.reits2.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.xyz.reits2.auth.jwt.JwtAuthenticationManager;
import com.xyz.reits2.auth.jwt.JwtAuthenticationWebFilter;
import com.xyz.reits2.auth.jwt.JwtTokenManager;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager();
    }

    @Bean
    public JwtAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager(jwtTokenManager());
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {

        JwtAuthenticationWebFilter jwtFilter = new JwtAuthenticationWebFilter(jwtAuthenticationManager());

        return http //
                .csrf().disable() //
                .httpBasic().disable() //
                .formLogin().disable() //
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION) //
                .authorizeExchange() //
                .pathMatchers(HttpMethod.POST, "/users", "/auth").permitAll() //
                .pathMatchers(HttpMethod.GET, "/users").hasRole("USER") //
                .anyExchange() //
                .authenticated() //
                .and() //
                .build();
    }
}
