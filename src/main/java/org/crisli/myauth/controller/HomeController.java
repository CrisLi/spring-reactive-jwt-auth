package org.crisli.myauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class HomeController {

    @RequestMapping
    public Mono<String> home() {
        return Mono.just("This is Reits2 auth");
    }
}
