package com.example.demo.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Pno;

    private String title;
    private String details;
    private String place;
    private String price;
    private String category;
    private String user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reg_date;

    private MultipartFile[] files;
    private boolean salesCompleted;
    private Long count;
    private String dirPath;
    private String filename;
    private String filesize;











}
