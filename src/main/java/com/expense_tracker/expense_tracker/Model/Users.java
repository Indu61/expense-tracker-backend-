package com.expense_tracker.expense_tracker.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Getter
@Setter
@Data
@Document(collection = "users") 
public class Users{
    @Id
    private String id; 
    private String username;
    private String email;
    private String password;
    private Date createdAt = new Date();
    public Users orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}