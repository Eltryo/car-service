package com.example.cargobay.controller;

import com.example.cargobay.entities.Car;
import com.example.cargobay.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<Car> getCars() {
        return carService.getCars();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCar(@RequestBody Car car) {
        carService.addCar(car);
    }

    @DeleteMapping("/delete/{carId}")
    @PreAuthorize("hasAuthority('car:write')")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }

    @PutMapping("/update/{carId}")
    @PreAuthorize("hasAuthority('car:write')")
    public void updateCar(@PathVariable Long carId, @RequestParam Integer horsePower) {
        carService.updateCar(carId, horsePower);
    }
}
