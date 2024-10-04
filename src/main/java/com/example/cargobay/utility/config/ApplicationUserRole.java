package com.example.cargobay.utility.config;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.cargobay.utility.config.ApplicationUserAuthority.CAR_READ;
import static com.example.cargobay.utility.config.ApplicationUserAuthority.CAR_WRITE;

@Getter
public enum ApplicationUserRole {
    USER(Sets.newHashSet(CAR_READ)),
    ADMIN(Sets.newHashSet(CAR_READ, CAR_WRITE));

    private final Set<ApplicationUserAuthority> permissions;

    ApplicationUserRole(Set<ApplicationUserAuthority> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = this.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
