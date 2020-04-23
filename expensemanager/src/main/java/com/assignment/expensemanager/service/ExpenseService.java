package com.assignment.expensemanager.service;

import com.assignment.expensemanager.dto.ApiRequest;
import com.assignment.expensemanager.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Double getExpenditure();

    Expense submitExpense(Expense expense);

    Expense updateExpense(Expense expense);

    Long deleteExpense(Long id);

    String sendMail(String email);
}
