package com.example.carrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.carrestapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
