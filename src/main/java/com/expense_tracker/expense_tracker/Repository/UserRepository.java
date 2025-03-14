package com.expense_tracker.expense_tracker.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.expense_tracker.expense_tracker.Model.Users;

public interface UserRepository extends MongoRepository<Users, String> {
    Users findByEmail(String email);
    Users findByUsername(String username);
}
