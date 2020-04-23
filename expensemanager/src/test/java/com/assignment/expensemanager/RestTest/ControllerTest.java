package com.assignment.expensemanager.RestTest;


import com.assignment.expensemanager.dto.ApiRequest;
import com.assignment.expensemanager.entity.Expense;
import com.assignment.expensemanager.rest.ExpenseRestController;
import com.assignment.expensemanager.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.containsString;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void findTotalExpenditure() throws Exception{
        Double sum=3000.00;
        when(expenseRestController.getTotalExpenditure()).thenReturn(sum);
        Double expected = 3000.00;
        String expectedString=Double. toString(expected);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/getTotalExpenditure")
                .accept((MediaType.APPLICATION_JSON));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals(expectedString, result.getResponse().getContentAsString(),false);
    }

    @Test
    public void addExpenditure() throws Exception{
        Expense expense=new Expense();
        expense.setCategory("Fruits");
        expense.setAmount(500.00);
        expense.setExpenseTitle("Drinking Water for April");
        expense.setMonthYear("2020-04-03");

        ResponseEntity responseEntity=new ResponseEntity<>(expense,HttpStatus.OK);

//        when(expenseRestController.saveExpense(expense)).thenReturn(responseEntity);


        String expected ="{\"category\":\"Water\",\"amount\":\"500\",\"expenseTitle\":\"Drinking Water for April\",\"monthYear\":\"2020-04-03\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = post("/api/v1/submitExpense").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(expense));

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
//                .andExpect(content().string("{\"percentCompleted\":98.0,\"startDate\":\"2/3/12\",\"endDate\":\"31/12/19\",\"count\":7,\"name\":\"Enterprise\"}"))
                .andReturn();
//        ResultActions resultActions = this.mockMvc.perform(request);
//        resultActions.andExpect(MockMvcResultMatchers.status.is(HttpsStatus.OK));
//        resultActions.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expense)));


        Assert.assertEquals(result.getResponse().getStatus(),HttpStatus.OK.value());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(),false);

    }

//    @Test
//    public void checkPostContents() throws Exception {
//        Expense expense=new Expense();
//        expense.setCategory("Electricity");
//        expense.setAmount(1000.00);
//        expense.setExpenseTitle("Electricity bill for the month of April");
//        expense.setMonthYear("2020-04-11");
//
//        String expected ="{\"category\":\"Electricity\",\"amount\":1000,\"expenseTitle\":\"Electricity bill for the month of April\",\"monthYear\":\"2020-04-11\"}";
//
//        when(expenseService.submitExpense(expense)).thenReturn(expense);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String convertedExpense=objectMapper.writeValueAsString(expense);
//        this.mockMvc.perform(post("/api/v1/submitExpense")).andExpect(status().isOk())
//                .andExpect(content().string(containsString(convertedExpense)));
//    }




//    @Test
//    public void deleteExpense(ApiRequest apiRequest) throws Exception {
//        apiRequest.setId(1);
//        when(expenseRestController.deleteExpense(apiRequest));
//        String expected ="Enterprise";
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .delete("/api/projects/Enterprise")
//                .accept((MediaType.APPLICATION_JSON));
//        MvcResult result = mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string("Enterprise")).andReturn();
//
//        //JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(),false);
//
//    }




}
