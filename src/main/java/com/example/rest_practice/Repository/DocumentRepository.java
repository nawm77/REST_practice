package com.example.rest_practice.Repository;

import com.example.rest_practice.Model.DocumentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentInformation, Long> {
    @Query(value = "SELECT * FROM document_information where customer_id = :id", nativeQuery = true)
    List<Object[]> findExistDocuments(@Param("id") Long id);
}
