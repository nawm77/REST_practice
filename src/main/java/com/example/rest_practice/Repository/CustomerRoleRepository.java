package com.example.rest_practice.Repository;

import com.example.rest_practice.Model.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Long> {
}
