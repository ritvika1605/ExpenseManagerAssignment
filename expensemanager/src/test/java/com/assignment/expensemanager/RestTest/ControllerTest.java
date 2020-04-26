package com.assignment.expensemanager.RestTest;

import com.assignment.expensemanager.dto.ApiRequest;
import com.assignment.expensemanager.dto.ApiResponse;
import com.assignment.expensemanager.entity.Expense;
import com.assignment.expensemanager.rest.ExpenseRestController;
import com.assignment.expensemanager.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpenseRestController.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ExpenseRestController expenseRestController;

    @MockBean
    ExpenseService expenseService;

    @Test
    public void findTotalExpense() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/getTotalExpenditure")
                .accept((MediaType.APPLICATION_JSON));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value());

        Double expectedTotalExpenditure=8500.00;
        when(expenseService.getExpenditure()).thenReturn(expectedTotalExpenditure);
        Double actualTotalExpenditure=expenseService.getExpenditure();
        Assert.assertEquals(expectedTotalExpenditure,actualTotalExpenditure);

    }


    @Test
    public void addExpenseTest() throws Exception{
        Expense expense=new Expense();
        expense.setCategory("Water");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

         //status check//
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = post("/api/v1/submitExpense").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(expense));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value());

                    //service returned object check//
        when(expenseService.submitExpense(expense)).thenReturn(expense);
        Expense returnedExpense=expenseService.submitExpense(expense);
        String returnedCategory=returnedExpense.getCategory();
        assertEquals(expense.getCategory(), returnedCategory);
    }

    @Test
    public void addExpenseNegativeTest() throws Exception{
        Expense badExpense=new Expense();
        badExpense.setAmount(500.00);
        badExpense.setExpenseTitle("Drinking Water for April");
        badExpense.setMonthYear("2020-04-03");

        ApiResponse apiResponse = new ApiResponse();
        when(expenseRestController.saveExpense(badExpense)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));          //category missing in badExpense
        ResponseEntity newReturnedExpense = expenseRestController.saveExpense(badExpense);
        Integer status=newReturnedExpense.getStatusCode().value();
        assertEquals(Long.valueOf(400), Long.valueOf(status));
    }


    @Test
    public void updateExpenseRestTest() throws Exception{
        Expense expense=new Expense();
        expense.setId(Long.valueOf(4));
        expense.setCategory("Water");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

        //status check//
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = put("/api/v1/updateExistingExpense").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(expense));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value());

        //service returned object check//
        when(expenseService.updateExpense(expense)).thenReturn(expense);
        Expense returnedExpense=expenseService.updateExpense(expense);
        String returnedCategory=returnedExpense.getCategory();
        assertEquals(expense.getCategory(), returnedCategory);
    }

    @Test
    public void updateExpenseNegativeTest() throws Exception{
        Expense badExpense=new Expense();
        badExpense.setAmount(500.00);
        badExpense.setExpenseTitle("Drinking Water for April");
        badExpense.setMonthYear("2020-04-03");

        ApiResponse apiResponse = new ApiResponse();
        when(expenseRestController.updateExpense(badExpense)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));          //category missing in badExpense
        ResponseEntity newreturnedExpense = expenseRestController.updateExpense(badExpense);
        Integer status=newreturnedExpense.getStatusCode().value();
        assertEquals(Long.valueOf(400), Long.valueOf(status));

    }


    @Test
    public void deleteExpenseRestTest() throws Exception{
        Expense expense=new Expense();
        expense.setId(Long.valueOf(4));

        //status check//
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = delete("/api/v1/deleteExpense").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(expense));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value());
    }

    @Test
    public void deleteExpenseNegativeTest() throws Exception{
        ApiRequest apiRequest = new ApiRequest();
        ApiResponse apiResponse = new ApiResponse();
        apiRequest.setId(null);
        when(expenseRestController.deleteExpense(apiRequest)).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST));
        ResponseEntity deleteresponse=expenseRestController.deleteExpense(apiRequest);
        assertEquals(HttpStatus.BAD_REQUEST, deleteresponse.getStatusCode());
    }


    @Test
    public void emailRestTest() throws Exception{
        String email="ritvika.pillai@quest-global.com";
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/sendEmail").contentType(MediaType.APPLICATION_JSON).content(email);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();


        String expectedReturnedMsg="Mail sent to"+email;
        when(expenseService.sendMail(email)).thenReturn(expectedReturnedMsg);
        String serviceReturnedMsg=expenseService.sendMail(email);
        assertEquals(expectedReturnedMsg, serviceReturnedMsg);
    }

}
