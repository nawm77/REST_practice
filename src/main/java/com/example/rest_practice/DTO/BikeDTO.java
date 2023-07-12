package com.example.rest_practice.DTO;

public record BikeDTO(Integer id, String make, String model, String serialNumber, Double costPerHour, Double costPerDay, String status) {
    @Override
    public String toString() {
        return "BikeDTO{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", costPerHour=" + costPerHour +
                ", costPerDay=" + costPerDay +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String make() {
        return make;
    }

    @Override
    public String model() {
        return model;
    }

    @Override
    public String serialNumber() {
        return serialNumber;
    }

    @Override
    public Double costPerHour() {
        return costPerHour;
    }

    @Override
    public Double costPerDay() {
        return costPerDay;
    }

    @Override
    public String status() {
        return status;
    }
}
