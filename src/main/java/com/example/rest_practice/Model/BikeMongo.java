package com.example.rest_practice.Model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "bikes")
public class BikeMongo {

    @Id
    private String id;
    private String name;
    private String owner;
    private String type;
    private Integer index;
    @Field("pricePerHour")
    private Integer pricePerHour;
}
