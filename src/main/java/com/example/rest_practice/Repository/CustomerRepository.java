package com.example.rest_practice.Repository;

import com.example.rest_practice.Model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAllByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.isNonBlocked = false WHERE c.id = :id")
    void blockCustomerById(@Param("id") Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.isNonBlocked = true WHERE c.id = :id")
    void unblockCustomerById(@Param("id") Long id);
}
