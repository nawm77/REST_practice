package com.example.rest_practice.Controller;

import com.example.rest_practice.Model.BikeMongo;
import com.example.rest_practice.MongoRepository.BikeRepositoryMongo;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {
    private final BikeRepositoryMongo bikeRepositoryMongo;
    private final MeterRegistry meterRegistry;

    @Autowired
    public TestController(BikeRepositoryMongo bikeRepositoryMongo, MeterRegistry meterRegistry) {
        this.bikeRepositoryMongo = bikeRepositoryMongo;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/{partition}")
    public ResponseEntity<?> getAll(@PathVariable("partition") Integer partition) {
        partition--;
        try {
            Timer t = meterRegistry.timer("Response time", "Method", "GET");
            LocalDateTime start = LocalDateTime.now();
            List<BikeMongo> list = bikeRepositoryMongo.findAll().stream()
                    .skip(20L * partition)
                    .limit(20L * (partition + 1))
                    .toList();
            LocalDateTime end = LocalDateTime.now();
            long durationInMillis = Duration.between(start, end).toMillis();
            t.record(durationInMillis, TimeUnit.MILLISECONDS);
            return ResponseEntity.status(HttpStatus.FOUND).body(list);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid partition value " + ++partition);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name){
        return ResponseEntity.ok(bikeRepositoryMongo.findByName(name));
    }

}
