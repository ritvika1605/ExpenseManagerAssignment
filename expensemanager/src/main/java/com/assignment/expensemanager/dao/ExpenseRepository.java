package com.assignment.expensemanager.dao;

import com.assignment.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query(value = "select sum(amount) from expense",nativeQuery = true)
    Double getTotalExpenditure();

    @Query(value = "select sum(amount) from expense",nativeQuery = true)
    Double findExpenses();
}
