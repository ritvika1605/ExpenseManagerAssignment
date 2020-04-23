package com.assignment.expensemanager.rest;

import com.assignment.expensemanager.constants.URLMappings;
import com.assignment.expensemanager.dto.ApiRequest;
import com.assignment.expensemanager.dto.ApiResponse;
import com.assignment.expensemanager.entity.Expense;
import com.assignment.expensemanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(URLMappings.REQUEST_MAPPING_URI)
@CrossOrigin(origins = "*", allowedHeaders = "*",exposedHeaders="token" )
public class ExpenseRestController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping(URLMappings.GET_TOTAL_EXPENDITURE)
    public Double getTotalExpenditure() {
        Double totalExpenditure = expenseService.getExpenditure();
        System.out.println(totalExpenditure);
        return totalExpenditure;
    }

    @PostMapping(URLMappings.SUBMIT_EXPENSE)
    public ResponseEntity saveExpense(@RequestBody Expense expense) throws ParseException{
        ApiResponse apiResponse = new ApiResponse();

        if(expense.getCategory()==null | expense.getAmount()==null | expense.getExpenseTitle()==null |expense.getMonthYear()==null )
        {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            apiResponse.setMessage("Check your Request");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        else{
            Expense expenses = expenseService.submitExpense(expense);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Success");
            apiResponse.setData(expenses);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
    }

    @PutMapping(URLMappings.UPDATE_EXPENSE)
    public ResponseEntity updateExpense(@RequestBody Expense expense) throws ParseException{
        ApiResponse apiResponse = new ApiResponse();

        if(expense.getCategory()==null | expense.getAmount()==null | expense.getExpenseTitle()==null |expense.getMonthYear()==null )
        {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            apiResponse.setMessage("Check your Request");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        else{
            Expense expenses = expenseService.updateExpense(expense);
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Success");
            apiResponse.setData("Expense updated successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping(URLMappings.DELETE_EXPENSE)
    public ResponseEntity deleteExpense(@RequestBody ApiRequest apiRequest) throws ParseException{
        ApiResponse apiResponse = new ApiResponse();
        if(apiRequest.getId()==null)
        {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            apiResponse.setMessage("Id is missing");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        else{
            Long deletedId=expenseService.deleteExpense(apiRequest.getId());
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Success");
            apiResponse.setData("Expense delete successfully");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
    }


    @PostMapping(URLMappings.SEND_MAIL)
    public ResponseEntity sendExpenseEmail(@RequestBody ApiRequest apiRequest){
        ApiResponse apiResponse = new ApiResponse();
        System.out.println(apiRequest.getEmail());
        String emailSentMessage=expenseService.sendMail(apiRequest.getEmail());
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }


    }



