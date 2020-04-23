package com.assignment.expensemanager.ServiceTest;

import com.assignment.expensemanager.dao.ExpenseRepository;
import com.assignment.expensemanager.emailservice.NotificationService;
import com.assignment.expensemanager.entity.Expense;
import com.assignment.expensemanager.service.ExpenseService;
import com.assignment.expensemanager.service.impl.ExpenseServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServiceImplTest {
    @InjectMocks
    ExpenseServiceImpl expenseServiceImpl;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    private NotificationService notificationservice;

    @Test
    public void findTotalExpenseServiceTest() throws Exception{
        Double sum=3500.00;
        when(expenseRepository.getTotalExpenditure()).thenReturn(sum);
        Double returnedSumByService=expenseServiceImpl.getExpenditure();
        assertEquals(sum, returnedSumByService);
    }

    @Test
    public void addExpenseServiceTest() throws Exception{
        Expense expense=new Expense();
        expense.setCategory("Water");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

        when(expenseRepository.save(expense)).thenReturn(expense);
        Expense returnedSavedExpense=expenseServiceImpl.submitExpense(expense);
        assertEquals(expense, returnedSavedExpense);
    }

    @Test
    public void updateExpenseServiceTest() throws Exception{
        Expense expense=new Expense();
        expense.setCategory("Water");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

        when(expenseRepository.saveAndFlush(expense)).thenReturn(expense);
        Expense returnedUpdatedExpense=expenseServiceImpl.updateExpense(expense);
        assertEquals(expense, returnedUpdatedExpense);
    }

    @Test
    public void deleteExpenseServiceTest() throws Exception{
        Integer id=3;
        Long longId=Long.valueOf(id);
        Long returnedDeletedId=expenseServiceImpl.deleteExpense(longId);
        assertEquals(longId, returnedDeletedId);
    }

    @Test
    public void emailServiceTest() throws Exception{
        Double sum=3500.00;
        String email="ritvika.pillai@mobiliya.com";
        String returnedMsg="Mail sent to"+email;
        when(expenseRepository.findExpenses()).thenReturn(sum);
        when(notificationservice.sendNotification(email,sum)).thenReturn(returnedMsg);

        String mailSentMsg=expenseServiceImpl.sendMail(email);
        assertEquals(returnedMsg,mailSentMsg);
    }

}
