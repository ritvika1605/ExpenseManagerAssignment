package com.assignment.expensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequest {
    private Long id;
    private String category;
    private Double amount;
    private String expenseTitle;
    private String monthAndYear;
    private String email;
}
