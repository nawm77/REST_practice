package com.example.rest_practice.Service.Impl;

import com.example.rest_practice.DTO.DocumentDTO;
import com.example.rest_practice.Exception.DocumentAlreadyExistsException;
import com.example.rest_practice.Mapper.DocumentMapper;
import com.example.rest_practice.Model.DocumentInformation;
import com.example.rest_practice.Repository.DocumentRepository;
import com.example.rest_practice.Service.CustomerService;
import com.example.rest_practice.Service.DocumentService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final CustomerService customerService;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentServiceImpl(CustomerService customerService, DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.customerService = customerService;
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public void addNewDocumentByCustomerId(Long id, DocumentDTO dto, UserDetails userDetails) throws DocumentAlreadyExistsException, AccessDeniedException {
        if(documentRepository.findExistDocuments(id).size()!=0){
            throw new DocumentAlreadyExistsException("Document for user with id "+ id + " already exists");
        } else if (customerService.findById(id).getUsername().equals(userDetails.getUsername())){
            DocumentInformation doc = documentMapper.toDocument(dto, id);
            documentRepository.save(doc);
            customerService.setCustomerRole(id);
        } else{
            throw new AccessDeniedException("You do not have the rights to perform this action, change id");
        }
    }
}
