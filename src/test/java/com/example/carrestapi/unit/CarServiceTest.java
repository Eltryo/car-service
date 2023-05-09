package com.example.carrestapi.unit;

import com.example.carrestapi.cars.Car;
import com.example.carrestapi.repository.CarRepository;
import com.example.carrestapi.service.CarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    private CarService carService;
    private Car car;

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
        car = new Car(
                "Volkswagen",
                "Polo",
                LocalDate.of(2019, Month.JANUARY, 1),
                120
        );
    }

    @Test
    void getCarsTest() {
        carService.getCars();
        Mockito.verify(carRepository).findAll();
    }

    @Test
    void addCarTest() {
        carService.addCar(car);
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        Mockito.verify(carRepository).save(carArgumentCaptor.capture());
        Car capturedCar = carArgumentCaptor.getValue();
        Assertions.assertThat(car).isEqualTo(capturedCar);
    }

    @Test
    void addAlreadyExistingCarTest() {
        Optional<Car> selectedCar = carRepository.findAllByDateOfManufactureAndModel(car.getDateOfManufacture(), car.getModel());
        given(selectedCar).willReturn(Optional.of(car));
        Assertions.assertThatThrownBy(() -> carService.addCar(car))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Car already exists");
    }

    @Test
    void deleteNonExistingCarTest() {
        Assertions.assertThatThrownBy(() -> carService.deleteCar(car.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Car doesn't exist");
    }

    @Test
    void deleteCartTest() {
        given(carRepository.findById(car.getId()))
                .willReturn(Optional.of(car));
        carService.deleteCar(car.getId());
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(carRepository).deleteById(longArgumentCaptor.capture());
        Long capturedCarId = longArgumentCaptor.getValue();
        Assertions.assertThat(car.getId()).isEqualTo(capturedCarId);
    }

    @Test
    void updateNonExistingCarTest() {
        Assertions.assertThatThrownBy(() -> carService.updateCar(car.getId(), car.getHorsePower()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Car doesn't exist");
    }

    @Test
    void updateExistingCarTest() {
        int updatedHorsePower = 200;
        given(carRepository.findById(car.getId()))
                .willReturn(Optional.of(car));
        carService.updateCar(car.getId(), updatedHorsePower);
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        Mockito.verify(carRepository).save(carArgumentCaptor.capture());
        Car capturedCar = carArgumentCaptor.getValue();
        Assertions.assertThat(200).isEqualTo(capturedCar.getHorsePower());
        Assertions.assertThat(car.getId()).isEqualTo(capturedCar.getId());
    }
}
