package com.example.cargobay.entity;

import com.example.cargobay.utility.config.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplicationUserRole applicationUserRole;

    public Role(ApplicationUserRole role) {
        this.applicationUserRole = role;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.applicationUserRole.getAuthorities();
    }
}
