package com.example.beadprog;

import com.example.beadprog.exception.CarNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars")
@Validated
public class CarController {
    private static final Logger logInfo = LoggerFactory.getLogger("fileLoggerInfo");
    private static final Logger logError = LoggerFactory.getLogger("fileLoggerError");
    private final List<Car> cars = new ArrayList<>();

    @GetMapping
    public Object getAllCars(){
        logInfo.info("Autós lista lekérdezve: {}", cars);
        return (cars.isEmpty()) ? "Az autós lista üres." : cars;
    }

    // Autó létrehozása
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody @Valid Car car) {
        car.setId(generateCarId());
        cars.add(car);
        logInfo.info("Autó létrehozva: ID={} - gyártó='{}', sebesség='{}', hozzáadva='{}'", car.getId(), car.getGyarto(), car.getVegsebesseg(), car.getHozzaadva());
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    private Long generateCarId() {
        return cars.stream().mapToLong(Car::getId).max().orElse(0) + 1;
    }

    // Autók frissítése

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable("id") @Min(1) Long id, @RequestBody @Valid Car updatedCar) {
        String errorMessage = "Frissítési hiba. Az ID=" + id + " autó nem található.";
        Car car = getCarById(id, errorMessage);

        car.setGyarto(updatedCar.getGyarto());
        car.setVegsebesseg(updatedCar.getVegsebesseg());
        car.setTulajEmail(updatedCar.getTulajEmail());

        logInfo.info("ID={} autó frissítve. Új adatok: gyártó='{}', sebesség='{}', tulajEmail='{}'", id, updatedCar.getGyarto(), updatedCar.getVegsebesseg(), updatedCar.getTulajEmail());

        return ResponseEntity.ok(car);
    }

    // Autó lekérdezése azonosító alapján
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") @Min(1) Long id) {
        String errorMessage = "Lekérdezési hiba. Az ID=" + id + " autó nem található.";
        Car car = getCarById(id, errorMessage);

        logInfo.info("Autó lekérdezve: ID={}, gyártó='{}', sebesség='{}', tulajEmail='{}', hozzáadva='{}'", id, car.getGyarto(), car.getVegsebesseg(), car.getTulajEmail(), car.getHozzaadva());

        return ResponseEntity.ok(car);
    }

    // Autó törlése

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") @Min(1) Long id) {
        String errorMessage = "Törlési hiba. Az ID=" + id + " autó nem található.";
        Car car = getCarById(id, errorMessage);

        cars.remove(car);
        String infoMessage = "Autó törölve. ID=" + id + ", gyártó='" + car.getGyarto() + "'.";
        logInfo.info(infoMessage);
        return ResponseEntity.ok(infoMessage);
    }

    private Car getCarById(Long id, String errorMessage) {
        return cars.stream().filter(car -> car.getId().equals(id)).findFirst().orElseThrow(() -> {
            logError.error(errorMessage);
            return new CarNotFoundException(errorMessage);
        });
    }
}
