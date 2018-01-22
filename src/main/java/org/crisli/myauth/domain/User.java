package org.crisli.myauth.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@CompoundIndexes({ @CompoundIndex(name = "user_login", def = "{'username': 1, 'org': 1}", unique = true) })
public class User extends BaseDomain implements UserDetails {

    private static final long serialVersionUID = -6172437549614626081L;

    @NonNull
    @NotEmpty
    private String username;

    @NotEmpty
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @NonNull
    @NotEmpty
    private String org;

    @NonNull
    private List<String> roles = emptyList();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream() //
                .map(role -> "ROLE_" + role) //
                .map(SimpleGrantedAuthority::new) //
                .collect(toList());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
