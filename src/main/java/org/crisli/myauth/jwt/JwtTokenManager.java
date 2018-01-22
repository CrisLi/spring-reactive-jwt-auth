package org.crisli.myauth.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.crisli.myauth.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenManager {

    private static Logger LOG = LoggerFactory.getLogger(JwtTokenManager.class);

    @Value("${jwt.secret:HelloKitty_MyNameIsChris}")
    private String secret;

    @Value("${jwt.timeout:10}")
    private Integer timeoutInMinutes;

    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

    @PostConstruct
    public void init() {
        this.secret = Base64.getEncoder().encodeToString(this.secret.getBytes());
    }

    @SuppressWarnings("unchecked")
    public User parse(String token) {

        try {
            Claims claims = Jwts.parser() //
                    .setSigningKey(secret) //
                    .parseClaimsJws(token) //
                    .getBody();

            User user = new User();

            user.setId(claims.get("id", String.class));
            user.setUsername(claims.get("username", String.class));
            user.setOrg(claims.get("org", String.class));
            user.setRoles(claims.get("roles", List.class));

            return user;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new BadCredentialsException(e.getMessage(), e);
        }

    }

    public String sign(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("org", user.getOrg());
        claims.put("roles", user.getRoles());

        LocalDateTime now = LocalDateTime.now();

        return Jwts.builder() //
                .setClaims(claims) //
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) //
                .setExpiration(Date.from(now.plusMinutes(timeoutInMinutes).atZone(ZoneId.systemDefault()).toInstant())) //
                .setSubject(user.getUsername()) //
                .signWith(algorithm, secret) //
                .compact(); //
    }

}
