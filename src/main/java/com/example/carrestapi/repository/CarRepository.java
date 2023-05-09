package com.example.carrestapi.repository;

import com.example.carrestapi.cars.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car as c WHERE c.dateOfManufacture = ?1 and c.model = ?2")
    Optional<Car> findAllByDateOfManufactureAndModel(LocalDate dateOfManufacture, String model);
}
