package com.xyz.reits2.auth.jwt;

import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    private ServerAuthenticationEntryPoint entryPoint = new HttpBasicServerAuthenticationEntryPoint();

    public JwtAuthenticationWebFilter(JwtAuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(this.entryPoint));
        this.setAuthenticationConverter(new JwtAuthenticationConverter());
    }

}
