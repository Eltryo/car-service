package com.example.cargobay.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "cars")
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String manufacturer;
    private String model;
    private LocalDate dateOfManufacture;
    private Integer horsePower;

    public Car(String manufacturer, String model, LocalDate dateOfManufacture, Integer horsePower) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.dateOfManufacture = dateOfManufacture;
        this.horsePower = horsePower;
    }

    public Car() {
    }
}
