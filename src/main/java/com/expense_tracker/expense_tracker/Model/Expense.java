package com.expense_tracker.expense_tracker.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "expenses") // MongoDB Collection
public class Expense {
    @Id
    private String id; // MongoDB ID

    @DBRef
    private Users user; // Reference to User entity
    private double amount;
    private String description;
    private String category;
    private String paymentMethod;
    private Date createdAt = new Date();

}
