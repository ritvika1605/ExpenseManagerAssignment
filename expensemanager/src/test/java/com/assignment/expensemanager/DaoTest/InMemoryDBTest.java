package com.assignment.expensemanager.DaoTest;

import com.assignment.expensemanager.configuration.config;
import com.assignment.expensemanager.dao.ExpenseRepository;
import com.assignment.expensemanager.entity.Expense;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { config.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTest {
    @Resource
    private ExpenseRepository expenseRepository;

    @Test
    public void getTotalExpenseTest(){
        Double expectedTotalExpense=8500.00;
        Double returnedTotalExpense=expenseRepository.getTotalExpenditure();
        assertEquals(expectedTotalExpense,returnedTotalExpense);
    }

    @Test
    public void addExpenseTest(){
        Expense expense=new Expense();
        expense.setCategory("Water");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

        Expense returnedExpense=expenseRepository.save(expense);
        assertEquals(expense,returnedExpense);
    }

    @Test
    public void updateExpenseTest(){
        Expense expense=new Expense();
        expense.setCategory("Water");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

        Expense returnedExpense=expenseRepository.saveAndFlush(expense);
        assertEquals(expense,returnedExpense);
    }

    @Test
    public void getTotalExpenseforEmail(){
        Double expectedTotalExpense=8500.00;
        Double falseTotalExpense=100.00;
        Double returnedTotalExpense=expenseRepository.findExpenses();
        assertEquals(expectedTotalExpense,returnedTotalExpense);
    }
}
