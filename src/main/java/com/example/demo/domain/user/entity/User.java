package com.example.demo.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id

    private String Id;
    private String password;
    private String username;
    private String role;
    private String phone;
    private String NickName;
    private Date reg_Date;

    // OAuth2 Added
    private String provider;
    private String providerId;
}
