package org.crisli.myauth.controller;

import org.crisli.myauth.domain.User;
import org.crisli.myauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Mono<User> create(@RequestBody @Validated User user) {
        return userService.create(user);
    }

    @GetMapping
    public Flux<User> getAll() {
        return userService.findAll();
    }

}
