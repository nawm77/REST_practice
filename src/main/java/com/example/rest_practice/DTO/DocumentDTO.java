package com.example.rest_practice.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentDTO {
    @Min(value = 1000, message = "Series min value 1000")
    @Max(value = 9999, message = "Series max value 9999")
    private Integer series;
    @Min(value = 100000, message = "Number min value 100000")
    @Max(value = 999999, message = "Number max value 999999")
    private Integer number;
    private LocalDateTime issueDate;
}
