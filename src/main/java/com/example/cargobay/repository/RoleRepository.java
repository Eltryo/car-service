package com.example.cargobay.repository;

import com.example.cargobay.entity.Role;
import com.example.cargobay.utility.config.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT role FROM Role as role WHERE role.applicationUserRole = ?1")
    Role findByName(ApplicationUserRole name);
}
