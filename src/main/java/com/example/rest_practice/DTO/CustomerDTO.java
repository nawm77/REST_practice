package com.example.rest_practice.DTO;

public record CustomerDTO(Long id, String name, String surname, String phoneNumber, String email, Double rate, Integer rentCount) {
    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", rate=" + rate +
                ", rentCount=" + rentCount +
                '}';
    }
}
