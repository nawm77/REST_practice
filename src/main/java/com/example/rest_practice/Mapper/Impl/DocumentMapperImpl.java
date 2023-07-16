package com.example.rest_practice.Mapper.Impl;

import com.example.rest_practice.DTO.DocumentDTO;
import com.example.rest_practice.Mapper.DocumentMapper;
import com.example.rest_practice.Model.Customer;
import com.example.rest_practice.Model.DocumentInformation;
import com.example.rest_practice.Service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentMapperImpl implements DocumentMapper {
    private final ModelMapper modelMapper;
    private final CustomerService customerService;

    @Autowired
    public DocumentMapperImpl(ModelMapper modelMapper, CustomerService customerService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @Override
    public DocumentInformation toDocument(DocumentDTO dto, Long id) {
        DocumentInformation documentInformation = modelMapper.map(dto, DocumentInformation.class);
        Customer customer = customerService.findById(id);
        documentInformation.setCustomer(customer);
        return documentInformation;
    }

    @Override
    public DocumentDTO toDTO(DocumentInformation document) {
        return modelMapper.map(document, DocumentDTO.class);
    }
}
