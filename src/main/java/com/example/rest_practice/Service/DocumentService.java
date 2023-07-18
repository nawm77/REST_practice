package com.example.rest_practice.Service;

import com.example.rest_practice.DTO.DocumentDTO;
import com.example.rest_practice.Exception.DocumentAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;

public interface DocumentService {
    void addNewDocumentByCustomerId(Long id, DocumentDTO dto, UserDetails userDetails) throws DocumentAlreadyExistsException, AccessDeniedException;
}
