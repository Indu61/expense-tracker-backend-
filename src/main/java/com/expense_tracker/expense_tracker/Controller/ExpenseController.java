package com.expense_tracker.expense_tracker.Controller;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.expense_tracker.Model.Expense;
import com.expense_tracker.expense_tracker.Model.Users;
import com.expense_tracker.expense_tracker.Repository.ExpenseRepository;
import com.expense_tracker.expense_tracker.Repository.UserRepository;
import com.expense_tracker.expense_tracker.Service.DateService;
import com.expense_tracker.expense_tracker.Service.JWTservice;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseController {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTservice jwtService;

    @Autowired
    private DateService dateService;


    @PostMapping("/addExpense")
        public ResponseEntity<?> addExpense(@RequestBody Expense expense, @RequestHeader("Authorization") String token) {
            try {
                System.out.println("token "+token);
                // Extract the username from the token
                String username = jwtService.extractUserName(token.replace("Bearer ", ""));

                System.out.println("username "+username);

                // Fetch the user from the database using the username
                Users user = userRepository.findByUsername(username);
                if (user == null) {
                   throw new RuntimeException("User not found");
                   }

                // Set the user in the expense object
                expense.setUser(user);

                // Save the expense to the database
                Expense savedExpense = expenseRepository.save(expense);
                return ResponseEntity.ok(savedExpense);
         } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
           }
        }

    @PutMapping("/updateExpense/{expenseId}")
    public ResponseEntity<?> updateExpense(
        @PathVariable String expenseId, 
        @RequestBody Expense updatedExpense, 
        @RequestHeader("Authorization") String token) {
        try {
            // Extract username from token
            String username = jwtService.extractUserName(token.replace("Bearer ", ""));
        
            // Fetch the user from the database using the username
            Users user = userRepository.findByUsername(username);
            if (user == null) {
               throw new RuntimeException("User not found");
        }

        // Find the existing expense by ID
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if (!optionalExpense.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }

        Expense existingExpense = optionalExpense.get();

        // Ensure that the expense belongs to the authenticated user
        if (!existingExpense.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to update this expense");
        }

        // Update expense details
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setDescription(updatedExpense.getDescription());
        existingExpense.setCreatedAt(updatedExpense.getCreatedAt());

        // Save the updated expense
        Expense savedExpense = expenseRepository.save(existingExpense);
        return ResponseEntity.ok(savedExpense);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating expense");
    }
}

@DeleteMapping("/deleteExpense/{expenseId}")
public ResponseEntity<?> deleteExpense(
        @PathVariable String expenseId, 
        @RequestHeader("Authorization") String token) {
    try {
        // Extract username from token
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));
        
        // Fetch the user from the database using the username
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        // Find the expense by ID
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if (!optionalExpense.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }

        Expense existingExpense = optionalExpense.get();

        // Ensure that the expense belongs to the authenticated user
        if (!existingExpense.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to delete this expense");
        }

        // Delete the expense
        expenseRepository.delete(existingExpense);
        return ResponseEntity.ok(Map.of("message", "Expense deleted successfully"));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting expense");
    }
}

@GetMapping("/getExpenseByid/{expenseId}")
public ResponseEntity<?> getExpenseById(
        @PathVariable String expenseId, 
        @RequestHeader("Authorization") String token) {
    try {
        // Extract username from token
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));
        
        // Fetch the user from the database using the username
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        // Find the expense by ID
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if (!optionalExpense.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expense not found");
        }

        Expense existingExpense = optionalExpense.get();

        // Ensure that the expense belongs to the authenticated user
        if (!existingExpense.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to delete this expense");
        }


        return ResponseEntity.ok(existingExpense);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting expense");
    }
}







    // @GetMapping("/getallexpenses")
    // public List<Expense> getAllExpenses() {
    //     return expenseRepository.findAll();
    // }

    @GetMapping("/getallexpenses")
    public ResponseEntity<?> getAllExpenses(@RequestHeader("Authorization") String token) {
    try {
        System.out.println("token "+token);
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));

        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Expense> expenses = expenseRepository.findByUser(user);

        return ResponseEntity.ok(expenses);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
    }
}


 @GetMapping("/gettodayexpenses")
    public ResponseEntity<?> getTodayExpenses(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("token "+token);
            String username = jwtService.extractUserName(token.replace("Bearer ", ""));

            System.out.println("Username "+username);

            Users user = userRepository.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Calculate start and end of the day
            Date[] startAndEndOfDay = dateService.getStartAndEndOfDay();
            Date startOfDay = startAndEndOfDay[0];
            Date endOfDay = startAndEndOfDay[1];

            System.out.println("Start of day "+startOfDay);
            System.out.println("End of day "+endOfDay);

            List<Expense> todayExpenses = expenseRepository.findByUserAndCreatedAtBetween(user, startOfDay, endOfDay);

            return ResponseEntity.ok(todayExpenses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
        }
    }

@GetMapping("/getLast7Daysexpenses")
public ResponseEntity<?> get7Last7DaysExpenses(@RequestHeader("Authorization") String token) {
    try {
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

       // Calculate start and end of the last 7 days
        Date[] startAndEndOfLast7Days = dateService.getStartAndEndOfLast7Days();
        Date startOfLast7Days = startAndEndOfLast7Days[0];
        Date endOfLast7Days = startAndEndOfLast7Days[1];

        System.out.println("Start of last 7 days: " + startOfLast7Days);
        System.out.println("End of last 7 days: " + endOfLast7Days);

        List<Expense> last7DaysExpenses = expenseRepository.findByUserAndCreatedAtBetween(user, startOfLast7Days, endOfLast7Days);
        return ResponseEntity.ok(last7DaysExpenses);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
    }
}

@GetMapping("/getLast30Daysexpenses")
public ResponseEntity<?> getLast30DaysExpenses(@RequestHeader("Authorization") String token) {
    try {
        String username = jwtService.extractUserName(token.replace("Bearer ", ""));
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

       // Calculate start and end of the last 7 days
        Date[] startAndEndOfLast30Days = dateService.getStartAndEndOfLast30Days();
        Date startOfLast30Days = startAndEndOfLast30Days[0];
        Date endOfLast30Days = startAndEndOfLast30Days[1];

        System.out.println("Start of last 30 days: " + startOfLast30Days);
        System.out.println("End of last 30 days: " + endOfLast30Days);

        List<Expense> last7DaysExpenses = expenseRepository.findByUserAndCreatedAtBetween(user, startOfLast30Days, endOfLast30Days);
        return ResponseEntity.ok(last7DaysExpenses);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
    }
}


}
