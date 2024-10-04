package com.example.cargobay.utility.config;

import com.example.cargobay.entity.Role;
import com.example.cargobay.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {
    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            roleRepository.save(new Role(ApplicationUserRole.USER));
            roleRepository.save(new Role(ApplicationUserRole.ADMIN));
        };
    }
}