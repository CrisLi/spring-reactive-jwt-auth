package com.xyz.reits2.auth.domain;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@CompoundIndexes({ @CompoundIndex(name = "user_login", def = "{'username': 1, 'org': 1}", unique = true) })
public class User extends BaseDomain {

    @NonNull
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NonNull
    @NotEmpty
    private String org;

}
