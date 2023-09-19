package com.example.rest_practice.MongoRepository;

import com.example.rest_practice.Model.BikeMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepositoryMongo extends MongoRepository<BikeMongo, String> {
    List<BikeMongo> findByName(String name);
}
