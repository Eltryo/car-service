package com.example.cargobay.entity;

import com.example.cargobay.utility.config.ApplicationUserRole;
import lombok.*;

import javax.persistence.*;

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
    private ApplicationUserRole name;

    public Role(ApplicationUserRole role) {
        this.name = role;
    }
}
