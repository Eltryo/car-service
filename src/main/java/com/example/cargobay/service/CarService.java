package com.example.cargobay.service;

import com.example.cargobay.entities.Car;
import com.example.cargobay.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public void addCar(Car car) {
        Optional<Car> selectedCar = carRepository.findAllByDateOfManufactureAndModel(car.getDateOfManufacture(), car.getModel());
        if (selectedCar.isPresent()) {
            throw new IllegalStateException("Car already exists");
        }

        carRepository.save(car);
    }

    public void deleteCar(Long carId) {
        Optional<Car> selectedCar = carRepository.findById(carId);
        if (selectedCar.isEmpty()) {
            throw new IllegalStateException("Car doesn't exist");
        }

        carRepository.deleteById(carId);
    }

    public void updateCar(Long carId, Integer horsePower) {
        Optional<Car> selectedCar = carRepository.findById(carId);
        if (selectedCar.isEmpty()) {
            throw new IllegalStateException("Car doesn't exist");
        }

        Car carInstance = selectedCar.get();
        carInstance.setHorsePower(horsePower);
        carRepository.save(carInstance);
    }
}
