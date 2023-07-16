package com.example.rest_practice.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentDTO {
    private Integer series;
    private Integer number;
    private LocalDateTime issueDate;
}
