package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String name;
    private String phone;
    private String nickname;

    private String role;
    private String Email;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reg_date;


    // OAuth2 Added
    private String provider;
    private String providerId;

    //profile이미지추가
    private String profileimage;
}
