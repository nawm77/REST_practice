package com.example.rest_practice.Controller;

import com.example.rest_practice.DTO.DocumentDTO;
import com.example.rest_practice.Exception.DocumentAlreadyExistsException;
import com.example.rest_practice.Service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveDocumentById(@PathVariable("id") Long id, @RequestBody @Valid DocumentDTO dto, @AuthenticationPrincipal UserDetails userDetails) throws DocumentAlreadyExistsException, AccessDeniedException {
        documentService.addNewDocumentByCustomerId(id, dto, userDetails);
        return ResponseEntity.status(204).build();
    }
}
