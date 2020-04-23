package com.assignment.expensemanager.service.impl;

import com.assignment.expensemanager.dao.ExpenseRepository;
import com.assignment.expensemanager.dto.ApiRequest;
import com.assignment.expensemanager.emailservice.NotificationService;
import com.assignment.expensemanager.entity.Expense;
import com.assignment.expensemanager.service.ExpenseService;
import com.assignment.expensemanager.utils.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private NotificationService notificationservice;

    @Override
    public Double getExpenditure() {
        Double total_sum=expenseRepository.getTotalExpenditure();
        return total_sum;
    }

    @Override
    public Expense submitExpense(Expense expense) {
        Expense toBeSavedExpense=expenseRepository.save(expense);
        return toBeSavedExpense;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Expense updatedExpense=expenseRepository.saveAndFlush(expense);
        return updatedExpense;
    }

    @Override
    public Long deleteExpense(Long id) {
        Long deletedId=id;
        if(expenseRepository.findById(id)!=null)
            expenseRepository.deleteById(id);

        else
            throw new DataNotFoundException("Unauthorized deletion");

        return deletedId;
    }

    @Override
    public String sendMail(String email) {
        Double totalExpense=expenseRepository.findExpenses();
        String returnedMessage=notificationservice.sendNotification(email,totalExpense);
        return (returnedMessage);
    }


}
