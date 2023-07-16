package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.DocumentDTO;
import com.example.rest_practice.Exception.DocumentAlreadyExistsException;

public interface DocumentService {
    void addNewDocumentByCustomerId(Long id, DocumentDTO dto) throws DocumentAlreadyExistsException;
}
