package com.expense_tracker.expense_tracker.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.expense_tracker.expense_tracker.Model.Expense;
import com.expense_tracker.expense_tracker.Model.Users;

public interface ExpenseRepository extends MongoRepository<Expense, String>{

    List<Expense> findByUser(Users user);
    //List<Expense> findById(String id);

    // @Query("{ 'createdAt' : { $gte: ?0, $lt: ?1 } }")
    // List<Expense> findTodayExpenses(Date startOfDay, Date endOfDay);

    // @Query("{ 'user': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    // List<Expense> findTodayExpensesByUser(Users user, Date startOfDay, Date endOfDay);

     List<Expense> findByUserAndCreatedAtBetween(Users user, Date startOfDay, Date endOfDay);

     @Query("{ 'user': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }")
     List<Expense> find7daysExpenses(Users user, Date startOfLast7Days, Date endOfLast7Days);

    
}
