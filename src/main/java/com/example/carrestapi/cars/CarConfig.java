package com.example.carrestapi.cars;

import com.example.carrestapi.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class CarConfig {
    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository) {
        return args -> {
            Car volkswagenPolo = new Car("Volkswagen", "Polo", LocalDate.of(2018, Month.JANUARY, 1), 65);
            carRepository.save(volkswagenPolo);
        };
    }
}
