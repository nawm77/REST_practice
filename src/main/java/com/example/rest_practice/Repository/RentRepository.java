package com.example.rest_practice.Repository;

import com.example.rest_practice.Model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentRequest, Long> {
    @Query("SELECT r from RentRequest r where r.renter.id = :renterId")
    List<RentRequest> findAllByCustomerId(@Param("renterId") Long id);
}
