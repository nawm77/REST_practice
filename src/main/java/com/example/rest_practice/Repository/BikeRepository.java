package com.example.rest_practice.Repository;

import com.example.rest_practice.Model.Bike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    Optional<Bike> findBikeBySerialNumber(String number);

    @Transactional
    @Modifying
    @Query("UPDATE Bike b set b.status='RENTED' where b.serialNumber= :number")
    void setRentedStatus(@Param("number") String number);

    @Transactional
    @Modifying
    @Query("UPDATE Bike b set b.status='AVAILABLE' where b.serialNumber= :number")
    void setAvailableStatus(@Param("number")String serialNumber);

    @Transactional
    @Modifying
    @Query("update Bike b set b.status='UNAVAILABLE' where b.serialNumber= :number")
    void setUnavailableStatusBySerialNumber(@Param("number") String number);


}
