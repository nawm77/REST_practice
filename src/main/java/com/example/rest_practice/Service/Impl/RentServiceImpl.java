package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.RentRequestDTO;
import com.example.rest_practice.DTO.Response.RentStopResponse;
import com.example.rest_practice.Mapper.RentRequestMapper;
import com.example.rest_practice.Model.RentRequest;
import com.example.rest_practice.Model.RentStatus;
import com.example.rest_practice.Repository.BikeRepository;
import com.example.rest_practice.Repository.RentRepository;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentServiceImpl implements RentService {
    private final RentRepository rentRepository;
    private final CustomerService customerService;
    private final RentRequestMapper rentRequestMapper;
    private final BikeRepository bikeRepository;

    @Autowired
    public RentServiceImpl(RentRepository rentRepository, CustomerService customerService, RentRequestMapper rentRequestMapper, BikeRepository bikeRepository) {
        this.rentRepository = rentRepository;
        this.customerService = customerService;
        this.rentRequestMapper = rentRequestMapper;
        this.bikeRepository = bikeRepository;
    }

    @Override
    public List<RentRequestDTO> findAllByCustomer(UserDetails userDetails) {
        return rentRepository.findAllByCustomerId(customerService.findByUsername(userDetails.getUsername()).get().getId())
                .stream()
                .map(r -> rentRequestMapper.toDTO(r, userDetails))
                .toList();
    }

    @Override
    public void startRent(RentRequestDTO rentRequestDTO, UserDetails userDetails) throws Exception {
        if (bikeRepository.findBikeBySerialNumber(rentRequestDTO.getBikeSerialNumber()).getStatus().equals(RentStatus.AVAILABLE)) {
            rentRepository.save(rentRequestMapper.toRentRequest(rentRequestDTO, userDetails));
            bikeRepository.setUnavailableStatus(rentRequestDTO.getBikeSerialNumber());
        } else {
            throw new Exception("Bike isn't available now for rental");
        }
    }

    @Override
    public void deleteById(Long id, UserDetails userDetails) throws AccessDeniedException {
        RentRequest r = rentRepository.findById(id).get();
        if (r.getRenter().getUsername().equals(userDetails.getUsername()) || userDetails.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            rentRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You don't have permission to delete this rent");
        }
    }

    @Override
    public RentStopResponse stopRentById(Long id, UserDetails userDetails, LocalDateTime time) throws AccessDeniedException {
        RentRequest r = rentRepository.findById(id).get();
        if (!r.getRenter().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You can't stop this rental");
        } else {
            double price = 0;
            double days = 0;
            double hours = 0;
            String totalTime = "";
            switch (r.getType()) {
                case DAY -> {
                    days = r.getTimeStart().until(time, ChronoUnit.DAYS);
                    hours = r.getTimeStart().until(time, ChronoUnit.HOURS) % 24;
                    System.out.println(days);
                    System.out.println(hours);
                    long addH = hours > 0 ? 1 : 0;
                    price = (days + addH) * r.getBike().getCostPerDay();
                    totalTime = days + " days and " + hours + " hours";
                }
                case HOUR -> {
                    hours = r.getTimeStart().until(time, ChronoUnit.HOURS) % 24;
                    days = r.getTimeStart().until(time, ChronoUnit.DAYS);
                    price = (days * 24 + hours) * r.getBike().getCostPerHour();
                    totalTime = days * 24 + hours + " hours";
                }
            }
            r.setTimeEnd(time);
            bikeRepository.setAvailableStatus(r.getBike().getSerialNumber());
            return RentStopResponse.builder()
                    .start(r.getTimeStart())
                    .end(r.getTimeEnd())
                    .price(price)
                    .totalTime(totalTime)
                    .build();
        }
    }
}
