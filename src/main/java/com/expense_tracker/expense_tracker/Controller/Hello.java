package com.expense_tracker.expense_tracker.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class Hello {

    @GetMapping("/hello")
    public String greet() {
        return "Hello";
    }
}
