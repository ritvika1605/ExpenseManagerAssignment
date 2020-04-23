package com.assignment.expensemanager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "EXPENSE_TITLE")
    private String expenseTitle;

    @Column(name = "MONTH_AND_YEAR")
    //@JsonFormat(pattern = "yyyy-MM-dd",timezone="IST")
    private String monthYear;

}
