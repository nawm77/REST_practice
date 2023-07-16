package com.example.rest_practice.Mapper;

import com.example.rest_practice.DTO.DocumentDTO;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.DocumentInformation;

public interface DocumentMapper {
    DocumentInformation toDocument(DocumentDTO dto, Long id);
    DocumentDTO toDTO (DocumentInformation document);
}
