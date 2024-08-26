package com.example.carrestapi.unit;

import com.example.carrestapi.entities.Car;
import com.example.carrestapi.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;
    Car car;

    @BeforeEach
    void setUp() {
        car = new Car(
                "Volkswagen",
                "Polo",
                LocalDate.of(2019, Month.JANUARY, 1),
                120
        );
        carRepository.save(car);
    }

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void findAllByDateOfManufactureAndModelTest() {
        LocalDate dateOfManufacture = LocalDate.of(2019, Month.JANUARY, 1);
        String model = "Polo";

        Optional<Car> selectedCar = carRepository.findAllByDateOfManufactureAndModel(dateOfManufacture, model);
        Optional<Car> selectedCar2 = carRepository.findAllByDateOfManufactureAndModel(dateOfManufacture, "Golf");

        Assertions.assertTrue(selectedCar.isPresent());
        Assertions.assertEquals(car, selectedCar.get());
        Assertions.assertFalse(selectedCar2.isPresent());
    }
}
