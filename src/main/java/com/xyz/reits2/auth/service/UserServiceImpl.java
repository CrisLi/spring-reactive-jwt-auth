package com.xyz.reits2.auth.service;

import static java.util.Collections.singletonList;
import static java.util.function.Function.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.xyz.reits2.auth.domain.User;
import com.xyz.reits2.auth.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    public static final String DEFAULT_ROLES = "USER";

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> create(User user) {
        if (CollectionUtils.isEmpty(user.getRoles())) {
            user.setRoles(singletonList(DEFAULT_ROLES));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String[] values = username.split("@");
        return userRepository.findByUsernameAndOrg(values[0], values[1]).map(identity());
    }

}
