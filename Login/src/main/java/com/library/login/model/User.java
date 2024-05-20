package com.library.login.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    @Id
    private String userID;
    private String password;
    private String Roles;
}
